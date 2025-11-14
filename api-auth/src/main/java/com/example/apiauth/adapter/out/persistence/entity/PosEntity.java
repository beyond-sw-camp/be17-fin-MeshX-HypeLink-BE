package com.example.apiauth.adapter.out.persistence.entity;

import com.example.apiauth.domain.model.value.SyncStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "pos")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String posCode; // Store Code + 01, 02, 03, 04 ...

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    private Boolean healthCheck;

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SyncStatus syncStatus = SyncStatus.NEW;

    @Builder
    private PosEntity(String posCode, StoreEntity store, Boolean healthCheck, MemberEntity member) {
        this.posCode = posCode;
        this.store = store;
        this.healthCheck = healthCheck;
        this.member = member;
    }
}