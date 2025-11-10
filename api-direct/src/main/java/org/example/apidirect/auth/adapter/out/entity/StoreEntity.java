package org.example.apidirect.auth.adapter.out.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String storeName;

    @Column(unique = true)
    private String storeCode;

    private String address;
    private String phone;
    private String businessNumber;

    @Builder
    private StoreEntity(Integer id, String storeName, String storeCode,
                       String address, String phone, String businessNumber) {
        this.id = id;
        this.storeName = storeName;
        this.storeCode = storeCode;
        this.address = address;
        this.phone = phone;
        this.businessNumber = businessNumber;
    }
}
