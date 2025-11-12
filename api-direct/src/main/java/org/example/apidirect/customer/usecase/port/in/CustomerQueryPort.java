package org.example.apidirect.customer.usecase.port.in;

import org.example.apidirect.customer.domain.Customer;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerQueryPort {
    CustomerResponse findById(Integer id);
    CustomerResponse findByPhone(String phone);
    CustomerResponse findByPhoneWithAvailableCoupons(String phone);
    Page<CustomerResponse> searchCustomers(String keyword, String ageGroup, Pageable pageable);
    Page<CustomerResponse> findAll(Pageable pageable);

    // Payment에서 사용하는 메서드
    Customer getCustomerForPayment(Integer customerId);
}
