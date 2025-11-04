package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.coupon.exception.CouponException;
import MeshX.HypeLink.head_office.customer.exception.CustomerException;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.coupon.exception.CouponExceptionType.COUPON_NOT_FOUNT;

@Repository
@RequiredArgsConstructor
public class CustomerCouponJpaRepositoryVerify {

    private final CustomerCouponRepository customerCouponRepository;

    public CustomerCoupon findById(Integer id) {
        Optional<CustomerCoupon> customerCoupon = customerCouponRepository.findById(id);
        if(customerCoupon.isPresent()) {
            return customerCoupon.get();
        }
        throw new CustomerException(COUPON_NOT_FOUNT);
    }


}
