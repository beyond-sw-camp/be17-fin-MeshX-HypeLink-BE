package org.example.apidirect.customer.adapter.out.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.apidirect.config.FeignGlobalConfig;
import org.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CustomerClient", url = "${head-office.service.url}", configuration = FeignGlobalConfig.class)
public interface CustomerFeignClient {

    Logger log = LoggerFactory.getLogger(CustomerFeignClient.class);

    @CircuitBreaker(name = "HEAD_OFFICE_CUSTOMER_CIRCUIT", fallbackMethod = "createFallback")
    @PostMapping("/api/customer/signup")
    CustomerResponse createCustomer(@RequestBody CustomerSignupCommand command);

    @CircuitBreaker(name = "HEAD_OFFICE_CUSTOMER_CIRCUIT", fallbackMethod = "getByPhoneFallback")
    @GetMapping("/api/customer/phone/{phone}")
    CustomerResponse getCustomerByPhone(@PathVariable String phone);

    @CircuitBreaker(name = "HEAD_OFFICE_CUSTOMER_CIRCUIT", fallbackMethod = "getByIdFallback")
    @GetMapping("/api/customer/{id}")
    CustomerResponse getCustomerById(@PathVariable Integer id);

    @CircuitBreaker(name = "HEAD_OFFICE_CUSTOMER_CIRCUIT", fallbackMethod = "getWithAvailableCouponsFallback")
    @GetMapping("/api/customer/phone/{phone}/available-coupons")
    CustomerResponse getCustomerWithAvailableCoupons(@PathVariable String phone);

    default CustomerResponse createFallback(CustomerSignupCommand command, Throwable cause) {
        log.error("본사 Customer 생성 API 호출 실패 (phone: {}): {}", command.getPhone(), cause.toString());
        return null;
    }

    default CustomerResponse getByPhoneFallback(String phone, Throwable cause) {
        log.error("본사 Customer 조회(phone) API 호출 실패 (phone: {}): {}", phone, cause.toString());
        return null;
    }

    default CustomerResponse getByIdFallback(Integer id, Throwable cause) {
        log.error("본사 Customer 조회(id) API 호출 실패 (id: {}): {}", id, cause.toString());
        return null;
    }

    default CustomerResponse getWithAvailableCouponsFallback(String phone, Throwable cause) {
        log.error("본사 Customer 조회(available-coupons) API 호출 실패 (phone: {}): {}", phone, cause.toString());
        return null;
    }
}
