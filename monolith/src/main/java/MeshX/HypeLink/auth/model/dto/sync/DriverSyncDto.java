package MeshX.HypeLink.auth.model.dto.sync;

import MeshX.HypeLink.auth.model.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverSyncDto {
    private Integer id;
    private String macAddress;
    private String carNumber;
    private Integer memberId;

    public static DriverSyncDto from(Driver driver) {
        return DriverSyncDto.builder()
                .id(driver.getId())
                .macAddress(driver.getMacAddress())
                .carNumber(driver.getCarNumber())
                .memberId(driver.getMember() != null ? driver.getMember().getId() : null)
                .build();
    }
}
