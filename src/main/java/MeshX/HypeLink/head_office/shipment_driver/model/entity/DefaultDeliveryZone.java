package MeshX.HypeLink.head_office.shipment_driver.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 초기 DB 세팅값 실제 Table은 DeliveryZone 에서 관리함
@Getter
@RequiredArgsConstructor
public enum DefaultDeliveryZone {
    SEOUL_A("SEOUL_A", "서울 강남권", "SEOUL", "강남/서초/송파 묶음"),
    SEOUL_B("SEOUL_B", "서울 북부권", "SEOUL", "노원/도봉/강북"),
    GYEONGGI_A("GYEONGGI_A", "경기 남부권", "GYEONGGI", "수원/용인/성남"),
    GYEONGGI_B("GYEONGGI_B", "경기 북부권", "GYEONGGI", "의정부/고양/남양주");

    private final String code;
    private final String name;
    private final String region;
    private final String description;
}
