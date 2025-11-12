package com.example.apiauth.adapter.out.persistence.read.entity;

import MeshX.common.BaseEntity;
import com.example.apiauth.domain.model.value.StoreState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "Store")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreReadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberReadEntity member;

    private Double lat;
    private Double lon;
    private Integer posCount;
    private String storeNumber;
    private StoreState storeState;

    // Read 전용 - 업데이트 메서드 없음
}
