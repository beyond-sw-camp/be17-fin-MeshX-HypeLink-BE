package com.example.apiauth.domain.model;

import com.example.apiauth.domain.model.value.StoreState;
import lombok.Builder;
import lombok.Getter;

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


}