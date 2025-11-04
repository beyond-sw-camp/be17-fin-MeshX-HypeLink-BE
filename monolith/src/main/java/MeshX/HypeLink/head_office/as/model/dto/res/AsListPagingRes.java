package MeshX.HypeLink.head_office.as.model.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AsListPagingRes {
    private List<AsListRes> asListResList;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    @Builder
    private AsListPagingRes(List<AsListRes> asListResList, Integer totalPages, Long totalElements, Integer currentPage, Integer pageSize) {
        this.asListResList = asListResList;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
