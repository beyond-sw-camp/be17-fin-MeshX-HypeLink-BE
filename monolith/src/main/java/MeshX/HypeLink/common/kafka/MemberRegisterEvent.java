package MeshX.HypeLink.common.kafka;

import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.model.entity.Region;
import MeshX.HypeLink.auth.model.entity.StoreState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterEvent {

    private Integer memberId;
    private String email;
    private String name;
    private String phone;
    private String address;
    private MemberRole role;
    private Region region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private StoreInfo storeInfo;
    private DriverInfo driverInfo;
    private PosInfo posInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreInfo {
        private Integer storeId;
        private Double lat;
        private Double lon;
        private String storeNumber;
        private StoreState storeState;
        private Integer posCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DriverInfo {
        private Integer driverId;
        private String macAddress;
        private String carNumber;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PosInfo {
        private Integer posId;
        private String posCode;
        private Integer storeId;
        private Boolean healthCheck;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
