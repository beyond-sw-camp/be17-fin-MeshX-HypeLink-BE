package org.example.apidirect.payment.adapter.out.persistence;

import org.example.apidirect.payment.adapter.out.entity.CustomerReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerReceiptRepository extends JpaRepository<CustomerReceiptEntity, Integer> {
    Optional<CustomerReceiptEntity> findByMerchantUid(String merchantUid);
}
