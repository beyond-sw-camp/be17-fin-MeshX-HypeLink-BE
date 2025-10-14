package MeshX.HypeLink.head_office.store.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;

    private String storeName;
    @Enumerated(EnumType.STRING)
    private StoreRole storeRole; // 권한 (본사 총 책임자, 본사 책임자, 직영점)

    private String storeCode; // 직영점 코드
    private String address;
    private Integer posCount; // 직영점 포스기 개수 (최소 2개)

    private Double latitude; // 위도
    private Double longitude; // 경도

    @Builder
    private StoreMember(String email, String password, String storeName,
                        StoreRole storeRole, String storeCode, String address,
                        Integer posCount, Double latitude, Double longitude) {
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.storeRole = storeRole;
        this.storeCode = storeCode;
        this.address = address;
        this.posCount = posCount;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updatePosNumber(Integer posCount) {
        this.posCount = posCount;
    }

    public void updateStoreAddress(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
