package com.example.apidirect.payment.adapter.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReceiptListPagingResponse {
    private List<ReceiptResponse> receipts;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;
}
