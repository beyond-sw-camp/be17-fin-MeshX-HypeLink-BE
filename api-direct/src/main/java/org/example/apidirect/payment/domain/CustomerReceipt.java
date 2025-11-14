package org.example.apidirect.payment.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CustomerReceipt {
    private Integer id;
    private String pgProvider;
    private String pgTid;
    private String merchantUid;
    private Integer totalAmount;
    private Integer discountAmount;
    private Integer couponDiscount;
    private Integer finalAmount;
    private Integer storeId;
    private Integer customerId;
    private String memberName;
    private String memberPhone;
    private String posCode;  // POS 코드 (JWT에서 추출)
    private PaymentStatus status;
    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        if (this.status == PaymentStatus.CANCELLED) {
            throw new IllegalStateException("취소된 영수증에는 항목을 추가할 수 없습니다");
        }
        this.orderItems.add(orderItem);
    }

    public void cancel() {
        if (this.status == PaymentStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 영수증입니다");
        }
        this.status = PaymentStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
    }

    public Integer calculateTotalAmount() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    public Integer calculateFinalAmount() {
        int total = calculateTotalAmount();
        int discount = (this.discountAmount != null ? this.discountAmount : 0);
        int coupon = (this.couponDiscount != null ? this.couponDiscount : 0);
        return total - discount - coupon;
    }

    public void validateReceiptData() {
        if (this.finalAmount < 0) {
            throw new IllegalStateException("최종 금액은 0보다 작을 수 없습니다");
        }

        Integer calculatedTotal = calculateTotalAmount();
        if (!this.totalAmount.equals(calculatedTotal)) {
            throw new IllegalStateException("총 금액이 주문 항목 합계와 일치하지 않습니다");
        }
    }

    public boolean isCancellable() {
        return this.status == PaymentStatus.PAID;
    }
}
