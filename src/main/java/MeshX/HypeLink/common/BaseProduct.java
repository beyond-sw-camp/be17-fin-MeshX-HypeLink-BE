package MeshX.HypeLink.common;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import MeshX.HypeLink.direct_store.item.repository.StoreCategoryRepository;
import MeshX.HypeLink.direct_store.item.repository.StoreItemDetailRepository;
import MeshX.HypeLink.direct_store.item.repository.StoreItemJpaRepositoryVerify;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2) // BaseMember 이후 실행
@RequiredArgsConstructor
public class BaseProduct {
    private final StoreJpaRepositoryVerify storeRepository;
    private final StoreCategoryRepository categoryRepository;
    private final StoreItemJpaRepositoryVerify storeItemRepository;
    private final StoreItemDetailRepository storeItemDetailRepository;

    @PostConstruct
    @Transactional
    public void init() {
        try {
            // Store1이 있는지 확인
            Store store1 = storeRepository.findById(1);



            initCategories(store1);
            initProducts(store1);
            log.info("✅ 의류 상품 더미데이터 생성 완료");

        } catch (RuntimeException e) {
            log.warn("Store 데이터가 없어 상품 데이터 생성 스킵: {}", e.getMessage());
        }
    }

    private void initCategories(Store store) {
        // 카테고리가 이미 존재하는지 확인
        if (categoryRepository.findByCategory("운동화").isEmpty()) {
            StoreCategory sneakers = StoreCategory.builder()
                    .category("운동화")
                    .build();
            categoryRepository.save(sneakers);
        }

        if (categoryRepository.findByCategory("티셔츠").isEmpty()) {
            StoreCategory tshirt = StoreCategory.builder()
                    .category("티셔츠")
                    .build();
            categoryRepository.save(tshirt);
        }

        if (categoryRepository.findByCategory("바지").isEmpty()) {
            StoreCategory pants = StoreCategory.builder()
                    .category("바지")
                    .build();
            categoryRepository.save(pants);
        }

        log.info("카테고리 생성 완료");
    }

    private void initProducts(Store store) {
        StoreCategory sneakers = categoryRepository.findByCategory("운동화")
                .orElseThrow(() -> new RuntimeException("운동화 카테고리 없음"));
        StoreCategory tshirt = categoryRepository.findByCategory("티셔츠")
                .orElseThrow(() -> new RuntimeException("티셔츠 카테고리 없음"));
        StoreCategory pants = categoryRepository.findByCategory("바지")
                .orElseThrow(() -> new RuntimeException("바지 카테고리 없음"));

        // 1. 나이키 에어포스 1
        StoreItem nikeAF1 = StoreItem.builder()
                .itemCode("NIKE-AF1-001")
                .koName("나이키 에어포스 1")
                .enName("Nike Air Force 1")
                .amount(129000)
                .unitPrice(129000)
                .content("클래식 농구화 디자인")
                .company("NIKE")
                .category(sneakers)
                .store(store)
                .build();
        storeItemRepository.save(nikeAF1);

        // 색상/사이즈 조합
        createItemDetail(nikeAF1, "화이트", "#FFFFFF", "250", 10);
        createItemDetail(nikeAF1, "화이트", "#FFFFFF", "255", 8);
        createItemDetail(nikeAF1, "화이트", "#FFFFFF", "260", 12);
        createItemDetail(nikeAF1, "화이트", "#FFFFFF", "265", 15);
        createItemDetail(nikeAF1, "화이트", "#FFFFFF", "270", 20);
        createItemDetail(nikeAF1, "블랙", "#000000", "250", 7);
        createItemDetail(nikeAF1, "블랙", "#000000", "255", 6);
        createItemDetail(nikeAF1, "블랙", "#000000", "260", 9);
        createItemDetail(nikeAF1, "블랙", "#000000", "265", 11);
        createItemDetail(nikeAF1, "블랙", "#000000", "270", 14);

        // 2. 아디다스 슈퍼스타
        StoreItem adidasSS = StoreItem.builder()
                .itemCode("ADIDAS-SS-001")
                .koName("아디다스 슈퍼스타")
                .enName("Adidas Superstar")
                .amount(119000)
                .unitPrice(119000)
                .content("아이코닉 쉘토 스니커즈")
                .company("ADIDAS")
                .category(sneakers)
                .store(store)
                .build();
        storeItemRepository.save(adidasSS);

        createItemDetail(adidasSS, "화이트/블랙", "#FFFFFF", "255", 10);
        createItemDetail(adidasSS, "화이트/블랙", "#FFFFFF", "260", 12);
        createItemDetail(adidasSS, "화이트/블랙", "#FFFFFF", "265", 8);
        createItemDetail(adidasSS, "화이트/블랙", "#FFFFFF", "270", 15);
        createItemDetail(adidasSS, "블랙/화이트", "#000000", "255", 6);
        createItemDetail(adidasSS, "블랙/화이트", "#000000", "260", 9);
        createItemDetail(adidasSS, "블랙/화이트", "#000000", "265", 7);
        createItemDetail(adidasSS, "블랙/화이트", "#000000", "270", 11);

        // 3. 나이키 드라이핏 티셔츠
        StoreItem nikeTee = StoreItem.builder()
                .itemCode("NIKE-TEE-001")
                .koName("나이키 드라이핏 티셔츠")
                .enName("Nike Dri-FIT Tee")
                .amount(35000)
                .unitPrice(35000)
                .content("땀 흡수 속건 기능")
                .company("NIKE")
                .category(tshirt)
                .store(store)
                .build();
        storeItemRepository.save(nikeTee);

        createItemDetail(nikeTee, "화이트", "#FFFFFF", "S", 20);
        createItemDetail(nikeTee, "화이트", "#FFFFFF", "M", 25);
        createItemDetail(nikeTee, "화이트", "#FFFFFF", "L", 30);
        createItemDetail(nikeTee, "화이트", "#FFFFFF", "XL", 15);
        createItemDetail(nikeTee, "블랙", "#000000", "S", 18);
        createItemDetail(nikeTee, "블랙", "#000000", "M", 22);
        createItemDetail(nikeTee, "블랙", "#000000", "L", 28);
        createItemDetail(nikeTee, "블랙", "#000000", "XL", 12);
        createItemDetail(nikeTee, "네이비", "#000080", "S", 15);
        createItemDetail(nikeTee, "네이비", "#000080", "M", 20);
        createItemDetail(nikeTee, "네이비", "#000080", "L", 25);
        createItemDetail(nikeTee, "네이비", "#000080", "XL", 10);

        // 4. 아디다스 트랙 팬츠
        StoreItem adidasPants = StoreItem.builder()
                .itemCode("ADIDAS-PANTS-001")
                .koName("아디다스 트랙 팬츠")
                .enName("Adidas Track Pants")
                .amount(65000)
                .unitPrice(65000)
                .content("클래식 3-스트라이프 디자인")
                .company("ADIDAS")
                .category(pants)
                .store(store)
                .build();
        storeItemRepository.save(adidasPants);

        createItemDetail(adidasPants, "블랙", "#000000", "S", 12);
        createItemDetail(adidasPants, "블랙", "#000000", "M", 15);
        createItemDetail(adidasPants, "블랙", "#000000", "L", 20);
        createItemDetail(adidasPants, "블랙", "#000000", "XL", 10);
        createItemDetail(adidasPants, "네이비", "#000080", "S", 10);
        createItemDetail(adidasPants, "네이비", "#000080", "M", 13);
        createItemDetail(adidasPants, "네이비", "#000080", "L", 18);
        createItemDetail(adidasPants, "네이비", "#000080", "XL", 8);

        // 5. 컨버스 척테일러
        StoreItem converse = StoreItem.builder()
                .itemCode("CONVERSE-CT-001")
                .koName("컨버스 척테일러 올스타")
                .enName("Converse Chuck Taylor All Star")
                .amount(79000)
                .unitPrice(79000)
                .content("타임리스 캔버스 스니커즈")
                .company("CONVERSE")
                .category(sneakers)
                .store(store)
                .build();
        storeItemRepository.save(converse);

        createItemDetail(converse, "화이트", "#FFFFFF", "250", 15);
        createItemDetail(converse, "화이트", "#FFFFFF", "260", 20);
        createItemDetail(converse, "화이트", "#FFFFFF", "270", 18);
        createItemDetail(converse, "블랙", "#000000", "250", 12);
        createItemDetail(converse, "블랙", "#000000", "260", 16);
        createItemDetail(converse, "블랙", "#000000", "270", 14);
        createItemDetail(converse, "레드", "#FF0000", "250", 8);
        createItemDetail(converse, "레드", "#FF0000", "260", 10);
        createItemDetail(converse, "레드", "#FF0000", "270", 9);

        log.info("상품 및 상품 상세 데이터 생성 완료");
    }

    private void createItemDetail(StoreItem item, String color, String colorCode, String size, Integer stock) {
        String itemDetailCode = item.getItemCode() + "-" +
                                color.replaceAll("[^A-Za-z0-9]", "") + "-" +
                                size;

        StoreItemDetail detail = StoreItemDetail.builder()
                .color(color)
                .colorCode(colorCode)
                .size(size)
                .stock(stock)
                .itemDetailCode(itemDetailCode)
                .item(item)
                .build();

        storeItemDetailRepository.save(detail);
    }
}
