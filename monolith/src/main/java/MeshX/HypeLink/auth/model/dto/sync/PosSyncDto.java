package MeshX.HypeLink.auth.model.dto.sync;

import MeshX.HypeLink.auth.model.entity.POS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosSyncDto {
    private Integer id;
    private String posCode;
    private Integer storeId;
    private Boolean healthCheck;
    private Integer memberId;

    public static PosSyncDto from(POS pos) {
        return PosSyncDto.builder()
                .id(pos.getId())
                .posCode(pos.getPosCode())
                .storeId(pos.getStore() != null ? pos.getStore().getId() : null)
                .healthCheck(pos.getHealthCheck())
                .memberId(pos.getMember() != null ? pos.getMember().getId() : null)
                .build();
    }
}
