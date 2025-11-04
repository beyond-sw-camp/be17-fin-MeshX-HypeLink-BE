package MeshX.HypeLink.head_office.customer.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CustomerInfoListRes {
    private List<CustomerInfoRes> customerInfoResList;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    @Builder
    private CustomerInfoListRes(List<CustomerInfoRes> customerInfoResList, Integer totalPages, Long totalElements, Integer currentPage, Integer pageSize) {
        this.customerInfoResList = customerInfoResList;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
