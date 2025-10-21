package MeshX.HypeLink.head_office.coupon.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CouponInfoListRes {
    private final List<CouponInfoRes> coupons;

    @Builder
    public CouponInfoListRes(List<CouponInfoRes> coupons) {
        this.coupons = coupons;
    }
}