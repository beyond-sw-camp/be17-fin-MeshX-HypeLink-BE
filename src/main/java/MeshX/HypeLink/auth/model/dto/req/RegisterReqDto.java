package MeshX.HypeLink.auth.model.dto.req;

import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.utils.geocode.model.dto.GeocodeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RegisterReqDto {
    // Common Member fields
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
                .posCount(0)
                .storeState(StoreState.TEMP_CLOSED)
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

    public POS toPosEntity(Member member,Store store) {
        return POS.builder()
                .store(store)
                .member(member)
                .posCode(this.posCode)
                .healthCheck(true)
                .build();
    }
}
