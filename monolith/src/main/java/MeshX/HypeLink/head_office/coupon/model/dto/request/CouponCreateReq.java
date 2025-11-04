package MeshX.HypeLink.head_office.coupon.model.dto.request;

import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.coupon.model.entity.CouponType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponCreateReq {
    private String name;
    private CouponType couponType;
    private Integer value;
    private String period;
    public Coupon toEntity(LocalDate[] dates) {
        return Coupon.builder()
                .name(name)
                .couponType(couponType)
                .value(value)
                .startDate(dates[0])
                .endDate(dates[1])
                .build();
    }
}
