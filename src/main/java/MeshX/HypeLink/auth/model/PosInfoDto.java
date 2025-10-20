package MeshX.HypeLink.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PosInfoDto {
    private Integer id;
    private String name;
    private String posCode;
}