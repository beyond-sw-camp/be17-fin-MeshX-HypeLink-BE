package org.example.apidirect.customer.adapter.out.feign;

import lombok.RequiredArgsConstructor;
import org.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import org.example.apidirect.customer.usecase.port.out.HeadOfficeCustomerFeignPort;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerFeignAdapter implements HeadOfficeCustomerFeignPort {

    private final CustomerFeignClient feignClient;

    @Override
    public CustomerResponse createCustomer(CustomerSignupCommand command) {
        return feignClient.createCustomer(command);
    }

    @Override
    public CustomerResponse getCustomerByPhone(String phone) {
        return feignClient.getCustomerByPhone(phone);
    }

    @Override
    public CustomerResponse getCustomerById(Integer id) {
        return feignClient.getCustomerById(id);
    }

    @Override
    public CustomerResponse getCustomerWithAvailableCoupons(String phone) {
        return feignClient.getCustomerWithAvailableCoupons(phone);
    }
}
