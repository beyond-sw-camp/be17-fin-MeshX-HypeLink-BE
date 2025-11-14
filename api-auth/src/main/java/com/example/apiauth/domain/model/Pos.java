package com.example.apiauth.domain.model;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.domain.model.value.SyncStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Pos {

    private Integer id;
    private String posCode;
    private Boolean healthCheck;
    private Store store;
    private Member member;

    private SyncStatus syncStatus;

    public static Pos createNew(
            Member member,
            Store store,
            String posCode
    ) {
        return Pos.builder()
                .member(member)
                .store(store)
                .posCode(posCode)
                .healthCheck(true)
                .syncStatus(SyncStatus.NEW)
                .build();
    }

    public MemberRegisterEvent.PosInfo toEventInfo() {
        return MemberRegisterEvent.PosInfo.builder()
                .posId(this.id)
                .posCode(this.posCode)
                .storeId(this.store.getId())
                .healthCheck(this.healthCheck)
                .createdAt(this.member != null ? this.member.getCreatedAt() : null)
                .updatedAt(this.member != null ? this.member.getUpdatedAt() : null)
                .build();
    }

}