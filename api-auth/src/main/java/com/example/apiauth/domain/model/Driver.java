package com.example.apiauth.domain.model;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.domain.model.value.SyncStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Driver {

    private Integer id;
    private String macAddress;
    private String carNumber;
    private Member member;

    private SyncStatus syncStatus;

    public static Driver createNew(
            Member member,
            String macAddress,
            String carNumber
    ) {
        return Driver.builder()
                .member(member)
                .macAddress(macAddress)
                .carNumber(carNumber)
                .syncStatus(SyncStatus.NEW)
                .build();
    }

    public MemberRegisterEvent.DriverInfo toEventInfo() {
        return MemberRegisterEvent.DriverInfo.builder()
                .driverId(this.id)
                .macAddress(this.macAddress)
                .carNumber(this.carNumber)
                .createdAt(this.member != null ? this.member.getCreatedAt() : null)
                .updatedAt(this.member != null ? this.member.getUpdatedAt() : null)
                .build();
    }

}