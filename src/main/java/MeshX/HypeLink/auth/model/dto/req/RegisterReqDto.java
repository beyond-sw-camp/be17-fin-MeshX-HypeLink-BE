package MeshX.HypeLink.auth.model.dto.req;

import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.utils.geocode.model.dto.GeocodeDto;
import lombok.Getter;

@Getter
public class RegisterReqDto {
    // Common Member fields
    private String email;
    private String password;
    private String name;
    private MemberRole role;
    private String phone;
    private Region region;

    // Store specific (for BRANCH_MANAGER)
    private Double lat;
    private Double lon;
    private String address;
    private Integer posCount;
    private String storeNumber;

    // Driver specific
    private String macAddress;
    private String carNumber;

    // POS specific
    private String posCode;
    private Integer storeId; // To link POS to a Store

    public Member toMemberEntity(String encodedPassword) {
        return Member.builder()
                .email(this.email)
                .password(encodedPassword)
                .name(this.name)
                .phone(this.phone)
                .address(this.address)
                .role(this.role)
                .region(this.region)
                .build();
    }

    public Store toStoreEntity(Member member,GeocodeDto geocodeDto) {
        return Store.builder()
                .member(member)
                .lat(geocodeDto.getLatAsDouble())
                .lon(geocodeDto.getLonAsDouble())
                .address(this.address)
                .posCount(this.posCount)
                .storeNumber(this.storeNumber)
                .build();
    }

    public Driver toDriverEntity(Member member) {
        return Driver.builder()
                .member(member)
                .macAddress(this.macAddress)
                .carNumber(this.carNumber)
                .build();
    }

    public POS toPosEntity(Member member, Store store) {
        return POS.builder()
                .member(member)
                .store(store)
                .posCode(this.posCode)
                .healthCheck(true)
                .build();
    }
}
