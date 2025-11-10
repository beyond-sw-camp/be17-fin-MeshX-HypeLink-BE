package com.example.apiauth.domain.model;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Pos {

    private Integer id;
    private String posCode;
    private Boolean healthCheck;
    private Store store;
    private Member member;

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
                .build();
    }

    public MemberRegisterEvent.PosInfo toEventInfo() {
        return MemberRegisterEvent.PosInfo.builder()
                .posId(this.id)
                .posCode(this.posCode)
                .storeId(this.store.getId())
                .healthCheck(this.healthCheck)
                .build();
    }

}