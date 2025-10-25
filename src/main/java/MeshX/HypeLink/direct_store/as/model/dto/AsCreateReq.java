package MeshX.HypeLink.direct_store.as.model.dto;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsStatus;
import lombok.Getter;

@Getter
public class AsCreateReq {

    private Integer storeId;
    private String title; // AS 게시글 제목
    private String description; // AS 게시글 내용

    public As toEntity(AsCreateReq dto, Store store){
        return As.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(AsStatus.PENDING)
                .store(store)
                .build();
    }
}
