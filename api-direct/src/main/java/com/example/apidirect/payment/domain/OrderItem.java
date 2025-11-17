package com.example.apidirect.payment.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderItem {
    private Integer id;
    private Integer customerReceiptId;
    private Integer storeItemDetailId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void validateQuantity() {
        if (this.quantity == null || this.quantity <= 0) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다");
        }
    }

    public void validatePrice() {
        if (this.unitPrice == null || this.unitPrice < 0) {
            throw new IllegalArgumentException("단가는 음수일 수 없습니다");
        }
    }

    public Integer calculateTotalPrice() {
        return this.unitPrice * this.quantity;
    }

    public boolean isPriceValid() {
        return this.totalPrice.equals(calculateTotalPrice());
    }
}
