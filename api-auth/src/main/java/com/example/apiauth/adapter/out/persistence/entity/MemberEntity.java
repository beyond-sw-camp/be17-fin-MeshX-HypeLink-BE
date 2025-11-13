package com.example.apiauth.adapter.out.persistence.entity;

import MeshX.common.BaseEntity;
import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@Table(name = "member")
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