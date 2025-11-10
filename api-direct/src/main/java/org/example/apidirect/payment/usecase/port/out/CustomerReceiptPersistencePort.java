package org.example.apidirect.payment.usecase.port.out;

import org.example.apidirect.payment.domain.CustomerReceipt;

import java.util.Optional;

public interface CustomerReceiptPersistencePort {
    CustomerReceipt save(CustomerReceipt receipt);
    Optional<CustomerReceipt> findByMerchantUid(String merchantUid);
    Optional<CustomerReceipt> findById(Integer id);
}
