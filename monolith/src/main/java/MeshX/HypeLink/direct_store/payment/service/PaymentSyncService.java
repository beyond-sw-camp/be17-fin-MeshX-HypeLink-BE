package MeshX.HypeLink.direct_store.payment.service;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import MeshX.HypeLink.direct_store.item.repository.StoreItemDetailJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.payment.consumer.dto.CustomerReceiptData;
import MeshX.HypeLink.direct_store.payment.consumer.dto.OrderItemData;
import MeshX.HypeLink.direct_store.payment.consumer.dto.PaymentData;
import MeshX.HypeLink.direct_store.payment.consumer.dto.PaymentSyncEvent;
import MeshX.HypeLink.direct_store.payment.model.entity.PaymentStatus;
import MeshX.HypeLink.direct_store.payment.model.entity.Payments;
import MeshX.HypeLink.direct_store.payment.repository.PaymentJpaRepositoryVerify;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import MeshX.HypeLink.head_office.customer.repository.CustomerJpaReceiptRepositoryVerify;
import MeshX.HypeLink.head_office.customer.repository.CustomerJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentSyncService {

    private final CustomerJpaReceiptRepositoryVerify receiptRepositoryVerify;
    private final PaymentJpaRepositoryVerify paymentRepositoryVerify;
    private final StoreJpaRepositoryVerify storeRepositoryVerify;
    private final CustomerJpaRepositoryVerify customerRepositoryVerify;
    private final StoreItemDetailJpaRepositoryVerify storeItemDetailRepositoryVerify;

    @Transactional
    public void syncPayment(PaymentSyncEvent event) {
        log.info("[SYNC] Payment 동기화 시작 - storeId: {}, merchantUid: {}",
                event.getStoreId(), event.getReceipt().getMerchantUid());

        // 중복 확인
        if (receiptRepositoryVerify.findByMerchantUid(event.getReceipt().getMerchantUid()).isPresent()) {
            log.warn("[SYNC] 이미 존재하는 Receipt - merchantUid: {}", event.getReceipt().getMerchantUid());
            return;
        }

        // 1. Store 조회
        Store store = storeRepositoryVerify.findById(event.getStoreId());

        // 2. Customer 조회 (nullable)
        Customer customer = null;
        if (event.getReceipt().getCustomerId() != null) {
            customer = customerRepositoryVerify.findById(event.getReceipt().getCustomerId());
        }

        // 3. CustomerReceipt 생성 및 저장
        CustomerReceipt receipt = toCustomerReceipt(event.getReceipt(), store, customer);

        // 4. OrderItem 생성 및 추가 (FK 검증 포함)
        for (OrderItemData itemData : event.getOrderItems()) {
            // StoreItemDetail 존재 확인
            StoreItemDetail storeItemDetail = findStoreItemDetail(itemData.getStoreItemDetailId());

            if (storeItemDetail == null) {
                log.warn("[SYNC] OrderItem 스킵 - StoreItemDetail 미존재: storeItemDetailId={}, merchantUid={}",
                        itemData.getStoreItemDetailId(), event.getReceipt().getMerchantUid());
                continue;
            }

            OrderItem orderItem = toOrderItem(itemData, storeItemDetail);
            receipt.addOrderItem(orderItem);
            log.info("[SYNC] OrderItem 추가 - storeItemDetailId: {}", itemData.getStoreItemDetailId());
        }

        // OrderItem이 하나도 없으면 저장 스킵
        if (receipt.getOrderItems().isEmpty()) {
            log.warn("[SYNC] OrderItem이 없어서 Payment 동기화 스킵 - merchantUid: {}", event.getReceipt().getMerchantUid());
            return;
        }
        // Store 재고 차감
        decreaseStoreItem(receipt);

        // CustomerReceipt 저장 (OrderItem cascade로 함께 저장)
        receiptRepositoryVerify.save(receipt);
        log.info("[SYNC] CustomerReceipt 저장 완료 - id: {}", receipt.getId());

        // 5. Payments 생성 및 저장
        Payments payment = toPayments(event.getPayment(), receipt);
        paymentRepositoryVerify.save(payment);
        log.info("[SYNC] Payments 저장 완료 - paymentId: {}", payment.getPaymentId());

        log.info("[SYNC] Payment 동기화 완료 - merchantUid: {}", event.getReceipt().getMerchantUid());
    }

    // Store 재고 차감
    private void decreaseStoreItem(CustomerReceipt receipt) {
        receipt.getOrderItems().forEach(one -> {
            StoreItemDetail storeItemDetail = one.getStoreItemDetail();

            storeItemDetail.updateStock(-1 * one.getQuantity());

            storeItemDetailRepositoryVerify.mergeItemDetail(storeItemDetail);
        });
    }

    private StoreItemDetail findStoreItemDetail(Integer storeItemDetailId) {
        try {
            return storeItemDetailRepositoryVerify.findById(storeItemDetailId);
        } catch (Exception e) {
            log.warn("[SYNC] StoreItemDetail 조회 실패 - id: {}, error: {}", storeItemDetailId, e.getMessage());
            return null;
        }
    }

    private CustomerReceipt toCustomerReceipt(CustomerReceiptData data, Store store, Customer customer) {
        return CustomerReceipt.builder()
                .pgProvider(data.getPgProvider())
                .pgTid(data.getPgTid())
                .merchantUid(data.getMerchantUid())
                .totalAmount(data.getTotalAmount())
                .discountAmount(data.getDiscountAmount())
                .couponDiscount(data.getCouponDiscount())
                .finalAmount(data.getFinalAmount())
                .store(store)
                .customer(customer)
                .memberName(data.getMemberName())
                .memberPhone(data.getMemberPhone())
                .status(MeshX.HypeLink.head_office.customer.model.entity.PaymentStatus.valueOf(data.getStatus()))
                .paidAt(data.getPaidAt())
                .build();
    }

    private OrderItem toOrderItem(OrderItemData data, StoreItemDetail storeItemDetail) {
        return OrderItem.builder()
                .storeItemDetail(storeItemDetail)
                .quantity(data.getQuantity())
                .unitPrice(data.getUnitPrice())
                .totalPrice(data.getTotalPrice())
                .build();
    }

    private Payments toPayments(PaymentData data, CustomerReceipt receipt) {
        return Payments.builder()
                .customerReceipt(receipt)
                .paymentId(data.getPaymentId())
                .transactionId(data.getTransactionId())
                .storeId(data.getStoreId())
                .channelKey(data.getChannelKey())
                .amount(data.getAmount())
                .status(PaymentStatus.valueOf(data.getStatus()))
                .paidAt(data.getPaidAt())
                .failureReason(data.getFailureReason())
                .build();
    }
}
