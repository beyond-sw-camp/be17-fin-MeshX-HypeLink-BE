package MeshX.HypeLink.head_office.customer.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReceiptListPagingRes {
    private List<ReceiptRes> receipts;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    @Builder
    private ReceiptListPagingRes(List<ReceiptRes> receipts, Integer totalPages, Long totalElements, Integer currentPage, Integer pageSize) {
        this.receipts = receipts;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
