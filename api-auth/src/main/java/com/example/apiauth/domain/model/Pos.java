package com.example.apiauth.domain.model;

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

}