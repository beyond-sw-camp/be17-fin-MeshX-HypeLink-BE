package com.example.apiauth.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Pos")
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

    @Builder
    private PosEntity(String posCode, StoreEntity store, Boolean healthCheck, MemberEntity member) {
        this.posCode = posCode;
        this.store = store;
        this.healthCheck = healthCheck;
        this.member = member;
    }
}