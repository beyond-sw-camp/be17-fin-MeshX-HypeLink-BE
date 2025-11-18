package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerReceiptRepository extends JpaRepository<CustomerReceipt, Integer> {
    Optional<CustomerReceipt> findByMerchantUid(String merchantUid);

    @Query("SELECT cr FROM CustomerReceipt cr WHERE cr.store.id = :storeId ORDER BY cr.paidAt DESC")
    List<CustomerReceipt> findByStoreIdOrderByPaidAtDesc(@Param("storeId") Integer storeId);

    @Query("SELECT cr FROM CustomerReceipt cr WHERE cr.store.id = :storeId ORDER BY cr.paidAt DESC")
    Page<CustomerReceipt> findByStoreIdOrderByPaidAtDesc(@Param("storeId") Integer storeId, Pageable pageable);

    @Query("SELECT cr FROM CustomerReceipt cr ORDER BY cr.id ASC")
    Page<CustomerReceipt> findAllByPage(Pageable pageable);
}
