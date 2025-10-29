package MeshX.HypeLink.head_office.coupon.model.dto.response;

import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponInfoRes {
    private Integer id;
    private String name;
    private String type;
    private Integer value;
    private String period;

    public static CouponInfoRes toDto(Coupon entity, String period) {
        return CouponInfoRes.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getCouponType().name())
                .value(entity.getValue())
                .period(period)
                .build();
    }

    @Builder
    private CouponInfoRes(Integer id, String name, String type, Integer value, String period) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.period = period;
    }
}