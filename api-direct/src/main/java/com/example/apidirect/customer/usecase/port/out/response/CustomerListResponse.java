package com.example.apidirect.customer.usecase.port.out.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListResponse {
    private List<CustomerResponse> customerInfoResList;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;
}
