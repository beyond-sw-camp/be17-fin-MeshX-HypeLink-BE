package org.example.apidirect.auth.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class POSEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String posCode;


    @Column(nullable = false)
    private Integer storeId;

    @Column(nullable = false)
    private Boolean healthCheck;

    @Column
    private Integer memberId;

    @Builder
    private POSEntity(Integer id, String posCode, Integer storeId, Boolean healthCheck, Integer memberId) {
        this.id = id;
        this.posCode = posCode;
        this.storeId = storeId;
        this.healthCheck = healthCheck;
        this.memberId = memberId;
    }

    public void updateHealthCheck(Boolean status) {
        this.healthCheck = status;
    }
}
