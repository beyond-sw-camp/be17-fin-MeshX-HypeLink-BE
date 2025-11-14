package com.example.apidirect.customer.usecase.port.out.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Integer id;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private List<CustomerCouponResponse> availableCoupons;
}
