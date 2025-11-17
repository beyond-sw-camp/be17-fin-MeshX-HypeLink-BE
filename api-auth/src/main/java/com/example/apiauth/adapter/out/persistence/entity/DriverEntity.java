package com.example.apiauth.adapter.out.persistence.entity;

import com.example.apiauth.domain.model.value.SyncStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "driver")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String macAddress;

    private String carNumber;
    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SyncStatus syncStatus = SyncStatus.NEW;

//    private DriverEntity(String macAddress, String carNumber, MemberEntity member) {
//        this.macAddress = macAddress;
//        this.carNumber = carNumber;
//        this.member = member;
//    }

    public void updateMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public void updateCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
