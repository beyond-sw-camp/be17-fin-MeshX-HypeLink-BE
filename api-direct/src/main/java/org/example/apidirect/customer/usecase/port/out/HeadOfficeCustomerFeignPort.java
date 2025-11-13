package org.example.apidirect.customer.usecase.port.out;

import org.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;

public interface HeadOfficeCustomerFeignPort {
    CustomerResponse createCustomer(CustomerSignupCommand command);
    CustomerResponse getCustomerByPhone(String phone);
    CustomerResponse getCustomerById(Integer id);
    CustomerResponse getCustomerWithAvailableCoupons(String phone);
}
