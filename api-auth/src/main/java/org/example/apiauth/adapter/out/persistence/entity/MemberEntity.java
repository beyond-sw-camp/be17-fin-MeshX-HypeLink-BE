package org.example.apiauth.adapter.out.persistence.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String name;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private Region region;


    @Column(nullable = true)
    private String refreshToken;


    @Builder
    private MemberEntity(String email, String password, String name, String phone,
                   String address, MemberRole role, Region region, String refreshToken) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.region = region;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateRegion(Region region) {
        this.region = region;
    }

}