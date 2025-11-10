package com.example.apiauth.domain.model;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Driver {

    private Integer id;
    private String macAddress;
    private String carNumber;
    private Member member;

    public static Driver createNew(
            Member member,
            String macAddress,
            String carNumber
    ) {
        return Driver.builder()
                .member(member)
                .macAddress(macAddress)
                .carNumber(carNumber)
                .build();
    }

    public MemberRegisterEvent.DriverInfo toEventInfo() {
        return MemberRegisterEvent.DriverInfo.builder()
                .driverId(this.id)
                .macAddress(this.macAddress)
                .carNumber(this.carNumber)
                .build();
    }

}