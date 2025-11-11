package com.example.apiauth.usecase.port.in;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCommand {
    private String email;
    private String password;
    private String name;
    private MemberRole role;
    private String phone;
    private Region region;

    private Double lat;
    private Double lon;
    private String address;
    private String storeNumber;

    private String macAddress;
    private String carNumber;

    private String posCode;
    private Integer storeId;

}
