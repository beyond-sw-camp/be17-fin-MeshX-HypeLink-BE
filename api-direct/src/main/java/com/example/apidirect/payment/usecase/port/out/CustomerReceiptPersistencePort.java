package com.example.apidirect.payment.usecase.port.out;

import com.example.apidirect.payment.domain.CustomerReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerReceiptPersistencePort {
    CustomerReceipt save(CustomerReceipt receipt);
    Optional<CustomerReceipt> findByMerchantUid(String merchantUid);
    Optional<CustomerReceipt> findById(Integer id);
    Page<CustomerReceipt> findAllByOrderByPaidAtDesc(Pageable pageable);
}
