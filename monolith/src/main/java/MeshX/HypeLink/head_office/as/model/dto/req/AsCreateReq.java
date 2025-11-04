package MeshX.HypeLink.head_office.as.model.dto.req;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.as.model.entity.As;
import lombok.Getter;

@Getter
public class AsCreateReq {
    private String title;
    private String description;

    public As toEntity(Store store) {
        return As.builder()
                .title(title)
                .description(description)
                .store(store)
                .build();
    }
}
