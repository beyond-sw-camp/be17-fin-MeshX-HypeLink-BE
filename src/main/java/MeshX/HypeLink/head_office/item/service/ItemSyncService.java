package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.repository.*;
import MeshX.HypeLink.head_office.item.service.dto.SaveStoreDetailReq;
import MeshX.HypeLink.head_office.item.service.dto.SaveStoreItemImageReq;
import MeshX.HypeLink.head_office.item.service.dto.SaveStoreItemListReq;
import MeshX.HypeLink.head_office.item.service.dto.SaveStoreItemReq;
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

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
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
    private final RetryTemplate retryTemplate;
    private final Executor storeSyncExecutor;

    private final WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(
                    HttpClient.create().responseTimeout(Duration.ofSeconds(10))
            ))
            .build();

    /**
     * ✅ 1️⃣ 매일 00시 실행 (오늘 수정된 상품만 직영점에 전송)
     */
    @Scheduled(cron = "0 0 0 * * *")
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
                                    sendItemsToStore(store.getId(), storeApiUrl, modifiedItems);
                                    return null;
                                }), storeSyncExecutor)
                        ).toArray(CompletableFuture[]::new)
        ).join();

        log.info("[BATCH] 상품 동기화 완료");
    }

    /**
     * ✅ 2️⃣ 신규 직영점 생성 시 전체 상품 페이징 전송
     */
    public void syncAllItemsForNewStore(Integer storeId) {
        Store store = storeJpaRepository.findById(storeId);
        String storeApiUrl = resolveStoreApiUrl(store);

        log.info("[SYNC] 신규 직영점({}) 전체 상품 전송 시작", storeId);

        int page = 0;
        int size = 100;
        Page<Item> pageResult;

        do {
            pageResult = itemRepository.findItemsWithPaging(PageRequest.of(page, size));
            List<Item> items = pageResult.getContent();

            if (!items.isEmpty()) {
                retryTemplate.execute(ctx -> {
                    sendItemsToStore(storeId, storeApiUrl, items);
                    return null;
                });
                log.info("[SYNC] 직영점({}) → page {}/{} 상품 {}개 전송 완료",
                        storeId, page + 1, pageResult.getTotalPages(), items.size());
            }
            page++;
        } while (page < pageResult.getTotalPages());

        log.info("[SYNC] 직영점({}) 전체 상품 초기 전송 완료", storeId);
    }

    /**
     * ✅ HQ → 직영점 서버로 상품 전송
     */
    private void sendItemsToStore(Integer storeId, String storeUrl, List<Item> items) {
        SaveStoreItemListReq payload = convertToStoreSyncDto(storeId, items);

        try {
            webClient.post()
                    .uri(storeUrl + "/api/store-item/create/all")
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
     * ✅ HQ → 직영점 DTO 변환 (빌더 기반)
     */
    private SaveStoreItemListReq convertToStoreSyncDto(Integer storeId, List<Item> items) {
        List<SaveStoreItemReq> itemReqList = items.stream().map(item -> {
            List<SaveStoreDetailReq> details = itemDetailRepository.findByItem(item).stream()
                    .map(d -> SaveStoreDetailReq.builder()
                            .size(d.getSize().getSize())
                            .color(d.getColor().getColorName())
                            .colorCode(d.getColor().getColorCode())
                            .stock(d.getStock())
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
     * ✅ 직영점의 실제 API URL 생성 로직
     * (직영점별 도메인 규칙에 따라 변경 가능)
     */
    private String resolveStoreApiUrl(Store store) {
        Member member = store.getMember();

        return "http://127.0.0.1:8080";
    }
}
