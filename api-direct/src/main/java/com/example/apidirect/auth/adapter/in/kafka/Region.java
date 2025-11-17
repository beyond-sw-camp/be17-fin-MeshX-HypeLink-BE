package com.example.apidirect.auth.adapter.in.kafka;

public enum Region {
    SEOUL_GYEONGGI("서울/경기"),
    GANGWON("강원"),
    CHUNGCHEONG("충청"),
    GYEONGSANG("경상"),
    JEOLLA("전라"),
    JEJU("제주");

    private final String region;

    Region(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}