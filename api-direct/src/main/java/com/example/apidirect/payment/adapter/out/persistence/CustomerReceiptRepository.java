package com.example.apidirect.payment.adapter.out.persistence;

import com.example.apidirect.payment.adapter.out.entity.CustomerReceiptEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomerReceiptRepository extends JpaRepository<CustomerReceiptEntity, Integer> {
    Optional<CustomerReceiptEntity> findByMerchantUid(String merchantUid);

    Page<CustomerReceiptEntity> findAllByOrderByPaidAtDesc(Pageable pageable);

    Page<CustomerReceiptEntity> findByStoreIdOrderByPaidAtDesc(Integer storeId, Pageable pageable);

    @Modifying
    @Query(value = """
        INSERT INTO customer_receipt (
            id, pg_provider, pg_tid, merchant_uid,
            total_amount, discount_amount, coupon_discount, final_amount,
            store_id, customer_id, member_name, member_phone, pos_code,
            status, paid_at, cancelled_at, created_at, updated_at
        )
        VALUES (
            :id, :pgProvider, :pgTid, :merchantUid,
            :totalAmount, :discountAmount, :couponDiscount, :finalAmount,
            :storeId, :customerId, :memberName, :memberPhone, :posCode,
            :status, :paidAt, :cancelledAt, :createdAt, :updatedAt
        )
        ON DUPLICATE KEY UPDATE
            pg_provider = VALUES(pg_provider),
            pg_tid = VALUES(pg_tid),
            merchant_uid = VALUES(merchant_uid),
            total_amount = VALUES(total_amount),
            discount_amount = VALUES(discount_amount),
            coupon_discount = VALUES(coupon_discount),
            final_amount = VALUES(final_amount),
            store_id = VALUES(store_id),
            customer_id = VALUES(customer_id),
            member_name = VALUES(member_name),
            member_phone = VALUES(member_phone),
            status = VALUES(status),
            paid_at = VALUES(paid_at),
            cancelled_at = VALUES(cancelled_at),
            updated_at = VALUES(updated_at)
        """, nativeQuery = true)
    void upsertReceipt(
            @Param("id") Integer id,
            @Param("pgProvider") String pgProvider,
            @Param("pgTid") String pgTid,
            @Param("merchantUid") String merchantUid,
            @Param("totalAmount") Integer totalAmount,
            @Param("discountAmount") Integer discountAmount,
            @Param("couponDiscount") Integer couponDiscount,
            @Param("finalAmount") Integer finalAmount,
            @Param("storeId") Integer storeId,
            @Param("customerId") Integer customerId,
            @Param("memberName") String memberName,
            @Param("memberPhone") String memberPhone,
            @Param("posCode") String posCode,
            @Param("status") String status,
            @Param("paidAt") LocalDateTime paidAt,
            @Param("cancelledAt") LocalDateTime cancelledAt,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("updatedAt") LocalDateTime updatedAt
    );
}
