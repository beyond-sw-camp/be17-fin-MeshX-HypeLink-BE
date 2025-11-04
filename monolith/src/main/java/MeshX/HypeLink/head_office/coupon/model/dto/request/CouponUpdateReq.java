package MeshX.HypeLink.head_office.coupon.model.dto.request;

import MeshX.HypeLink.head_office.coupon.model.entity.CouponType;
import lombok.Getter;

@Getter
public class CouponUpdateReq {
    private Integer id;
    private String name;
    private CouponType couponType;
    private Integer value;
    private String period;
}
