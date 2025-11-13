package org.example.apidirect.customer.adapter.out.feign;

import lombok.RequiredArgsConstructor;
import org.example.apidirect.customer.usecase.port.out.CouponFeignPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponFeignAdapter implements CouponFeignPort {

    private final CouponFeignClient couponFeignClient;

    @Override
    public void useCoupon(Integer customerCouponId) {
    }
}
