package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StoreItemDetail {
    private Integer id;
    private String itemDetailCode;
    private String itemCode;
    private Integer storeItemId;
    private String itemName;  // koName 또는 enName
    private Integer colorId;
    private Integer sizeId;
    private String color;
    private String colorCode;
    private String size;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateStock(Integer stockChange) {
        if (this.stock + stockChange < 0) {
            throw new IllegalStateException("재고가 0보다 작을 수 없습니다");
        }
        this.stock += stockChange;
    }

    public void decreaseStock(Integer quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("재고가 부족합니다");
        }
        this.stock -= quantity;
    }

    public void increaseStock(Integer quantity) {
        this.stock += quantity;
    }

    public boolean isLowStock(Integer threshold) {
        return this.stock <= threshold;
    }
}
