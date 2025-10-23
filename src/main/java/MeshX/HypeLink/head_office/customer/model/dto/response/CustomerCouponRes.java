package MeshX.HypeLink.head_office.customer.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.CustomerCoupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CustomerCouponRes {
    private Integer id; // CustomerCoupon's ID
    private Integer couponId;
    private String couponName;
    private String couponType;
    private Integer couponValue;
    private LocalDate issuedDate;
    private LocalDate expirationDate;
    private boolean isUsed;
    private LocalDate usedDate;

    public static CustomerCouponRes toDto(CustomerCoupon entity) {
        return CustomerCouponRes.builder()
                .id(entity.getId())
                .couponId(entity.getCoupon().getId())
                .couponName(entity.getCoupon().getName())
                .couponType(entity.getCoupon().getCouponType().name()) // Assuming CouponType is an Enum
                .couponValue(entity.getCoupon().getValue())
                .issuedDate(entity.getIssuedDate())
                .expirationDate(entity.getExpirationDate())
                .isUsed(entity.isUsed())
                .usedDate(entity.getUsedDate())
                .build();
    }
}
