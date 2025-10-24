package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreItemDetailRepository extends JpaRepository<StoreItemDetail, Integer> {
    List<StoreItemDetail> findAllByItemDetailCodeIn(List<String> itemDetailCodes);

    // 디테일 꼬드로 조회하기
    Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode);

    // 특정 매장의 전체 상품 조회 (페이징)
    @Query("SELECT d FROM StoreItemDetail d WHERE d.item.store.id = :storeId")
    Page<StoreItemDetail> findByStoreId(@Param("storeId") Integer storeId, Pageable pageable);

    // 특정 매장의 상품명 검색 (페이징) - 한글명, 영문명, 바코드 모두 검색
    @Query("SELECT d FROM StoreItemDetail d WHERE d.item.store.id = :storeId AND " +
           "(d.item.koName LIKE %:keyword% OR d.item.enName LIKE %:keyword% OR d.itemDetailCode LIKE %:keyword%)")
    Page<StoreItemDetail> findByStoreIdAndName(@Param("storeId") Integer storeId, @Param("keyword") String keyword, Pageable pageable);

    // 특정 매장의 카테고리별 조회 (페이징)
    @Query("SELECT d FROM StoreItemDetail d WHERE d.item.store.id = :storeId AND d.item.category.category = :category")
    Page<StoreItemDetail> findByStoreIdAndCategory(@Param("storeId") Integer storeId, @Param("category") String category, Pageable pageable);

    // 특정 매장의 재고 부족 상품 조회 (재고 N개 이하)
    @Query("SELECT d FROM StoreItemDetail d WHERE d.item.store.id = :storeId AND d.stock <= :minStock")
    Page<StoreItemDetail> findByStoreIdAndLowStock(@Param("storeId") Integer storeId, @Param("minStock") Integer minStock, Pageable pageable);

    // 특정 StoreItem의 모든 Detail 조회
    List<StoreItemDetail> findByItem(StoreItem item);
}
