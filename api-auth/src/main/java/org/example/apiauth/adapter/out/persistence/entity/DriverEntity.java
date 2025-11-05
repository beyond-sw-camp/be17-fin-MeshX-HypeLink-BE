package org.example.apiauth.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Driver")
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

    @Builder
    private DriverEntity(String macAddress, String carNumber, MemberEntity member) {
        this.macAddress = macAddress;
        this.carNumber = carNumber;
        this.member = member;
    }

    public void updateMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public void updateCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
