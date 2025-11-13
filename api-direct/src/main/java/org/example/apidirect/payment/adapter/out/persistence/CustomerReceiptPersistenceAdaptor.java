package org.example.apidirect.payment.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.payment.adapter.out.entity.CustomerReceiptEntity;
import org.example.apidirect.payment.adapter.out.entity.OrderItemEntity;
import org.example.apidirect.payment.adapter.out.mapper.CustomerReceiptMapper;
import org.example.apidirect.payment.adapter.out.mapper.OrderItemMapper;
import org.example.apidirect.payment.domain.CustomerReceipt;
import org.example.apidirect.payment.usecase.port.out.CustomerReceiptPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class CustomerReceiptPersistenceAdaptor implements CustomerReceiptPersistencePort {

    private final CustomerReceiptRepository customerReceiptRepository;

    @Override
    public CustomerReceipt save(CustomerReceipt receipt) {
        CustomerReceiptEntity entity = CustomerReceiptMapper.toEntity(receipt);

        // OrderItem 추가
        receipt.getOrderItems().forEach(orderItem -> {
            OrderItemEntity itemEntity = OrderItemMapper.toEntity(orderItem);
            entity.addOrderItem(itemEntity);
        });

        CustomerReceiptEntity saved = customerReceiptRepository.save(entity);
        return CustomerReceiptMapper.toDomain(saved);
    }

    @Override
    public Optional<CustomerReceipt> findByMerchantUid(String merchantUid) {
        return customerReceiptRepository.findByMerchantUid(merchantUid)
                .map(CustomerReceiptMapper::toDomain);
    }

    @Override
    public Optional<CustomerReceipt> findById(Integer id) {
        return customerReceiptRepository.findById(id)
                .map(CustomerReceiptMapper::toDomain);
    }

    @Override
    public Page<CustomerReceipt> findAllByOrderByPaidAtDesc(Pageable pageable) {
        return customerReceiptRepository.findAllByOrderByPaidAtDesc(pageable)
                .map(CustomerReceiptMapper::toDomain);
    }
}
