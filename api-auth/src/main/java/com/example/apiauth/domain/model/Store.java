package com.example.apiauth.domain.model;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.domain.model.value.StoreState;
import com.example.apiauth.domain.model.value.SyncStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Store {

    private Integer id;
    private Double lat;
    private Double lon;
    private Integer posCount;
    private String storeNumber;
    private StoreState storeState;
    private Member member;

    private SyncStatus syncStatus;

    public static Store createNew(
            Member member,
            Double lat,
            Double lon,
            String storeNumber
    ) {
        return Store.builder()
                .member(member)
                .lat(lat)
                .lon(lon)
                .storeNumber(storeNumber)
                .posCount(0)
                .storeState(StoreState.TEMP_CLOSED)
                .syncStatus(SyncStatus.NEW)
                .build();
    }

    public Store increasePosCount() {
        return Store.builder()
                .id(this.id)
                .member(this.member)
                .lat(this.lat)
                .lon(this.lon)
                .storeNumber(this.storeNumber)
                .posCount(this.posCount + 1)
                .storeState(this.storeState)
                .syncStatus(this.syncStatus)
                .build();
    }

    public Store decreasePosCount() {
        return Store.builder()
                .id(this.id)
                .member(this.member)
                .lat(this.lat)
                .lon(this.lon)
                .storeNumber(this.storeNumber)
                .posCount(this.posCount - 1)
                .storeState(this.storeState)
                .syncStatus(this.syncStatus)
                .build();
    }

    public MemberRegisterEvent.StoreInfo toEventInfo() {
        return MemberRegisterEvent.StoreInfo.builder()
                .storeId(this.id)
                .lat(this.lat)
                .lon(this.lon)
                .storeNumber(this.storeNumber)
                .storeState(this.storeState)
                .posCount(this.posCount)
                .createdAt(this.member != null ? this.member.getCreatedAt() : null)
                .updatedAt(this.member != null ? this.member.getUpdatedAt() : null)
                .build();
    }

}