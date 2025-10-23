package MeshX.HypeLink.head_office.customer.service;

import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.coupon.repository.CouponJpaRepositoryVerify;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerSignupReq;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerUpdateReq;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoListRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoRes;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerCoupon; // Import CustomerCoupon
import MeshX.HypeLink.head_office.customer.repository.CustomerJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException

import java.time.LocalDate; // Import LocalDate
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerJpaRepositoryVerify customerRepository;
    private final CouponJpaRepositoryVerify couponRepository;

    public void signup (CustomerSignupReq dto) {
        customerRepository.save(dto.toEntity());
    }

    public CustomerInfoRes findById (Integer id) {
        Customer customer = customerRepository.findById(id);
        return CustomerInfoRes.toDto(customer);
    }

    public CustomerInfoListRes readAll () {
        List<Customer> customers = customerRepository.readAll();
        List<CustomerInfoRes> customerInfoResList = CustomerInfoRes.toDtoList(customers);
        return CustomerInfoListRes.builder()
                .customerInfoResList(customerInfoResList)
                .build();
    }

    public CustomerInfoRes update(CustomerUpdateReq dto) {
        Customer customer = customerRepository.findById(dto.getId());

        if(dto.getPassword() != null && !dto.getPassword().isEmpty() ) {
            customer.updatePassword(dto.getPassword());
        }

        if(dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            customer.updatePhone(dto.getPhone());
        }

        customerRepository.save(customer);
        return CustomerInfoRes.toDto(customer);
    }

    public void issueCoupon(Integer customerId, Integer couponId) {
        Customer customer = customerRepository.findById(customerId);

        Coupon couponTemplate = couponRepository.findById(couponId);

        CustomerCoupon issuedCustomerCoupon = CustomerCoupon.builder()
                .customer(customer)
                .coupon(couponTemplate)
                .issuedDate(LocalDate.now())
                .expirationDate(couponTemplate.getEndDate())
                .isUsed(false)
                .build();

        customer.addCustomerCoupon(issuedCustomerCoupon);
        customerRepository.save(customer);
    }
}
