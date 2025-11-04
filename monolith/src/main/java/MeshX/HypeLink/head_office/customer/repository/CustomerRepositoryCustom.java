package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> searchCustomers(String keyword, String ageGroup);
    Page<Customer> searchCustomersPaged(String keyword, String ageGroup, Pageable pageable);
}
