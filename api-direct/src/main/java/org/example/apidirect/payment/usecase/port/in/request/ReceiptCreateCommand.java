package org.example.apidirect.payment.usecase.port.in.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReceiptCreateCommand {
    private Integer storeId;
    private Integer memberId;
    private String memberName;
    private String memberPhone;
    private Integer couponDiscount;
    private Integer customerCouponId;  // 사용한 고객 쿠폰 ID
    private List<ReceiptItemCommand> items;
}
