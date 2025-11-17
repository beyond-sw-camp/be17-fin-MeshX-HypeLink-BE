package com.example.apidirect.customer.adapter.out.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.example.apidirect.config.FeignGlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CouponClient", url = "${head-office.service.url}", configuration = FeignGlobalConfig.class)
public interface CouponFeignClient {
    Logger log = LoggerFactory.getLogger(CouponFeignClient.class);

    @CircuitBreaker(name = "COUPON_CIRCUIT", fallbackMethod = "useCouponFallback")
    @PatchMapping("/api/customer/coupons/{customerCouponId}/use")
    void useCoupon(@PathVariable Integer customerCouponId);

    default void useCouponFallback(Integer customerCouponId, Throwable cause) {
        log.error("쿠폰 사용 API 호출 실패 - couponId: {}", customerCouponId);
        // 실패 처리 (로깅만 하거나 예외 발생)
    }
}
