package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByPhone(String phone);

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.customerCoupons WHERE c.phone = :phone")
    Optional<Customer> findByPhoneWithCoupons(@Param("phone") String phone);
}
