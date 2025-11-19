package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.repository.StoreItemDetailJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.repository.StoreItemJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.repository.*;
import MeshX.HypeLink.head_office.item.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemSyncService {
    private final StoreJpaRepositoryVerify storeJpaRepository;
    private final ItemJpaRepositoryVerify itemRepository;
    private final ItemImageJpaRepositoryVerify itemImageRepository;
    private final ItemDetailJpaRepositoryVerify itemDetailRepository;
    private final CategoryJpaRepositoryVerify categoryRepository;
    private final StoreItemJpaRepositoryVerify storeItemRepository;
    private final StoreItemDetailJpaRepositoryVerify storeItemDetailRepository;

    private final RetryTemplate retryTemplate;
    private final Executor storeSyncExecutor;

    private final WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(
                    HttpClient.create()
                            .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)  // 10초 (연결)
                            .doOnConnected(conn -> conn
                                    .addHandlerLast(new ReadTimeoutHandler(600, TimeUnit.SECONDS))   // 10분 (읽기)
                                    .addHandlerLast(new WriteTimeoutHandler(600, TimeUnit.SECONDS))  // 10분 (쓰기)
                            )
                            .responseTimeout(Duration.ofMinutes(10))  // 10분 (전체 응답)
            ))
            .build();

    /**
     * 매일 00시 실행 (오늘 수정된 상품만 직영점에 전송)
     */
      @Scheduled(cron = "0 0 0 * * *") // 00시 00분에 실행
//    @Scheduled(cron = "0 * * * * *")
    public void syncNewItemsToStores() {
        LocalDate today = LocalDate.now();

        // 오늘 수정된 상품 조회
        List<Item> modifiedItems = itemRepository.findItems().stream()
                .filter(i -> i.getUpdatedAt() != null && i.getUpdatedAt().toLocalDate().isEqual(today))
                .toList();

        if (modifiedItems.isEmpty()) {
            log.info("[BATCH] 오늘({}) 수정된 상품 없음", today);
            return;
        }

        // 실제 존재하는 모든 직영점 조회
        List<Store> stores = storeJpaRepository.findAll();
        log.info("[BATCH] 상품 {}개, {}개 직영점에 동기화 시작", modifiedItems.size(), stores.size());

        // 병렬 처리로 전송
        CompletableFuture.allOf(
                stores.stream()
                        .map(store -> CompletableFuture.runAsync(() ->
                                retryTemplate.execute(ctx -> {
                                    String storeApiUrl = resolveStoreApiUrl(store);
//                                    syncAllItemsForNewStore(store.getId()); // 실제 스토어들에게 동기화 되는지 확인용 메서드
                                    sendCategoriesToStore(store.getId(), storeApiUrl);
                                    sendItemsToStore(store.getId(), storeApiUrl, modifiedItems);
                                    return null;
                                }), storeSyncExecutor)
                        ).toArray(CompletableFuture[]::new)
        ).join();

        log.info("[BATCH] 상품 동기화 완료");
    }

    private SaveStoreCategoriesReq convertToCategorySyncDto(Integer storeId) {
        List<Category> all = categoryRepository.findAll();

        List<SaveStoreCategoryReq> list = all.stream()
                .map(one -> SaveStoreCategoryReq.builder()
                        .category(one.getCategory())
                        .build()
                )
                .toList();

        return SaveStoreCategoriesReq.builder()
                .categories(list)
                .storeId(storeId)
                .build();
    }

    /**
     * HQ → 직영점 서버로 카테고리 전송
     */
    private void sendCategoriesToStore(Integer storeId, String storeUrl) {
        SaveStoreCategoriesReq payload = convertToCategorySyncDto(storeId);

        try {
            webClient.post()
                    .uri(storeUrl + "/api/direct/category/save/all")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> res.bodyToMono(String.class)
                            .map(err -> new RuntimeException("카테고리 응답 오류: " + err)))
                    .toBodilessEntity()
                    .block();

            log.info("직영점({}) [{}] 카테고리 {}개 전송 성공",
                    storeId, storeUrl, payload.getCategories().size());
        } catch (Exception e) {
            log.error("직영점({}) [{}] 카테고리 전송 실패: {}", storeId, storeUrl, e.getMessage());
            throw e;
        }
    }

    /**
     * 신규 직영점 생성 시 전체 상품 페이징 전송
     */
    public void syncAllItemsForNewStore(Integer storeId) {
        Store store = storeJpaRepository.findById(storeId);
        String storeApiUrl = resolveStoreApiUrl(store);

        log.info("[SYNC] 신규 직영점({}) 전체 상품 전송 시작", storeId);

        sendCategoriesToStore(storeId, storeApiUrl);

        // ✅ 페이징으로 처리
        int page = 0;
        int size = 1000;
        org.springframework.data.domain.Page<MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail> pageResult;

        do {
            pageResult = storeItemDetailRepository.findByStoreIdWithPage(storeId, PageRequest.of(page, size));
            List<MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail> batch = pageResult.getContent();

            if (!batch.isEmpty()) {
                retryTemplate.execute(ctx -> {
                    sendStoreItemsToStore(storeId, storeApiUrl, batch);
                    return null;
                });
                log.info("[SYNC] 직영점({}) → page {}/{} 상품 {}개 전송 완료",
                        storeId, page + 1, pageResult.getTotalPages(), batch.size());
            }
            page++;
        } while (page < pageResult.getTotalPages());

        log.info("[SYNC] 직영점({}) 전체 상품 초기 전송 완료", storeId);
    }

    /**
     * HQ → 직영점 서버로 상품 전송 (StoreItemDetail 기반)
     */
    private void sendStoreItemsToStore(Integer storeId, String storeUrl,
                                       List<MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail> storeItemDetails) {
        SaveStoreItemListReq payload = convertStoreItemsToSyncDto(storeId, storeItemDetails);

        try {
            webClient.post()
                    .uri(storeUrl + "/api/direct/store/item/create/all")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> res.bodyToMono(String.class)
                            .map(err -> new RuntimeException("응답 오류: " + err)))
                    .toBodilessEntity()
                    .block();

            log.info("직영점({}) [{}] 상품 전송 성공", storeId, storeUrl);
        } catch (Exception e) {
            log.error("직영점({}) [{}] 전송 실패: {}", storeId, storeUrl, e.getMessage());
            throw e;
        }
    }

    /**
     * HQ → 직영점 서버로 상품 전송 (Item 기반 - 배치용)
     */
    private void sendItemsToStore(Integer storeId, String storeUrl, List<Item> items) {
        SaveStoreItemListReq payload = convertToStoreSyncDto(storeId, items);

        try {
            webClient.post()
                    .uri(storeUrl + "/api/direct/store/item/create/all")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> res.bodyToMono(String.class)
                            .map(err -> new RuntimeException("응답 오류: " + err)))
                    .toBodilessEntity()
                    .block();

            log.info("직영점({}) [{}] 상품 {}개 전송 성공", storeId, storeUrl, items.size());
        } catch (Exception e) {
            log.error("직영점({}) [{}] 전송 실패: {}", storeId, storeUrl, e.getMessage());
            throw e;
        }
    }

    /**
     * StoreItemDetail 기반 → 직영점 DTO 변환 (id와 실제 재고 포함)
     */
    private SaveStoreItemListReq convertStoreItemsToSyncDto(
            Integer storeId,
            List<MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail> storeItemDetails) {

        // StoreItem별로 그룹핑
        java.util.Map<MeshX.HypeLink.direct_store.item.model.entity.StoreItem, List<MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail>> groupedByStoreItem =
                storeItemDetails.stream()
                        .collect(java.util.stream.Collectors.groupingBy(
                                MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail::getItem));

        List<SaveStoreItemReq> itemReqList = groupedByStoreItem.entrySet().stream()
                .map(entry -> {
                    MeshX.HypeLink.direct_store.item.model.entity.StoreItem storeItem = entry.getKey();
                    List<MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail> details = entry.getValue();

                    // StoreItemDetail → SaveStoreDetailReq (id와 실제 재고 포함)
                    List<SaveStoreDetailReq> detailReqList = details.stream()
                            .map(d -> SaveStoreDetailReq.builder()
                                    .id(d.getId())  // ✅ id 포함
                                    .size(d.getSize())
                                    .color(d.getColor())
                                    .colorCode(d.getColorCode())
                                    .stock(d.getStock())  // ✅ 실제 재고
                                    .itemDetailCode(d.getItemDetailCode())
                                    .build())
                            .toList();

                    // 이미지는 본사 Item에서 조회 (itemCode로 찾기)
                    List<SaveStoreItemImageReq> images = new java.util.ArrayList<>();
                    try {
                        Item item = itemRepository.findByItemCode(storeItem.getItemCode());
                        if (item != null) {
                            images = itemImageRepository.findByItem(item).stream()
                                    .map(img -> SaveStoreItemImageReq.builder()
                                            .originalFilename(img.getImage().getOriginalFilename())
                                            .savedPath(img.getImage().getSavedPath())
                                            .contentType(img.getImage().getContentType())
                                            .fileSize(img.getImage().getFileSize())
                                            .sortIndex(img.getSortIndex())
                                            .build())
                                    .toList();
                        }
                    } catch (Exception e) {
                        log.warn("이미지 조회 실패 - itemCode: {}", storeItem.getItemCode());
                    }

                    // StoreItem → SaveStoreItemReq (id 포함)
                    return SaveStoreItemReq.builder()
                            .id(storeItem.getId())  // ✅ id 포함
                            .category(storeItem.getCategory().getCategory())
                            .itemCode(storeItem.getItemCode())
                            .unitPrice(storeItem.getUnitPrice())
                            .amount(storeItem.getAmount())
                            .enName(storeItem.getEnName())
                            .koName(storeItem.getKoName())
                            .content(storeItem.getContent())
                            .company(storeItem.getCompany())
                            .images(images)
                            .itemDetails(detailReqList)
                            .build();
                })
                .toList();

        return SaveStoreItemListReq.builder()
                .storeId(storeId)
                .items(itemReqList)
                .build();
    }

    /**
     * HQ → 직영점 DTO 변환 (빌더 기반)
     */
    private SaveStoreItemListReq convertToStoreSyncDto(Integer storeId, List<Item> items) {
        List<SaveStoreItemReq> itemReqList = items.stream().map(item -> {
            List<SaveStoreDetailReq> details = itemDetailRepository.findByItem(item).stream()
                    .map(d -> SaveStoreDetailReq.builder()
                            .size(d.getSize().getSize())
                            .color(d.getColor().getColorName())
                            .colorCode(d.getColor().getColorCode())
                            .stock(0)
                            .itemDetailCode(d.getItemDetailCode())
                            .build())
                    .toList();

            List<SaveStoreItemImageReq> images = itemImageRepository.findByItem(item).stream()
                    .map(img -> SaveStoreItemImageReq.builder()
                            .originalFilename(img.getImage().getOriginalFilename())
                            .savedPath(img.getImage().getSavedPath())
                            .contentType(img.getImage().getContentType())
                            .fileSize(img.getImage().getFileSize())
                            .sortIndex(img.getSortIndex())
                            .build())
                    .toList();

            return SaveStoreItemReq.builder()
                    .category(item.getCategory().getCategory())
                    .itemCode(item.getItemCode())
                    .unitPrice(item.getUnitPrice())
                    .amount(item.getAmount())
                    .enName(item.getEnName())
                    .koName(item.getKoName())
                    .content(item.getContent())
                    .company(item.getCompany())
                    .images(images)
                    .itemDetails(details)
                    .build();
        }).toList();

        return SaveStoreItemListReq.builder()
                .storeId(storeId)
                .items(itemReqList)
                .build();
    }

    /**
     * 직영점의 실제 API URL 생성 로직
     * (직영점별 도메인 규칙에 따라 변경 가능)
     */
    private String resolveStoreApiUrl(Store store) {
        Member member = store.getMember();

        return "http://localhost:8081";
    }
}
