package org.example.apidirect.customer.usecase.port.out.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCouponResponse {
    private Integer id; // CustomerCoupon's ID
    private Integer couponId;
    private String couponName;
    private String couponType;
    private Integer couponValue;
    private LocalDate issuedDate;
    private LocalDate expirationDate;
    private Boolean isUsed;
    private LocalDate usedDate;
}
