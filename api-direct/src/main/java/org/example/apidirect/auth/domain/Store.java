package org.example.apidirect.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Store {
    private Integer id;
    private String storeName;
    private String storeCode;
    private String address;
    private String phone;
    private String businessNumber;
}
