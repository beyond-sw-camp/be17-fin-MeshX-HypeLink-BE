package com.example.apidirect.payment.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_receipt_id", nullable = false)
    private CustomerReceiptEntity customerReceipt;

    @Column(name = "store_item_detail_id", nullable = false)
    private Integer storeItemDetailId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer totalPrice;

    @Builder
    private OrderItemEntity(Integer id, Integer storeItemDetailId,
                           Integer quantity, Integer unitPrice, Integer totalPrice) {
        this.id = id;
        this.storeItemDetailId = storeItemDetailId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public void setCustomerReceipt(CustomerReceiptEntity customerReceipt) {
        this.customerReceipt = customerReceipt;
    }
}
