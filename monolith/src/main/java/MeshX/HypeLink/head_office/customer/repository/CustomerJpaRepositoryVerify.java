package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.head_office.customer.exception.CustomerException;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.customer.exception.CustomerExceptionType.DUPLICATE_PHONE;
import static MeshX.HypeLink.head_office.customer.exception.CustomerExceptionType.NOT_FOUNT;


@Repository
@RequiredArgsConstructor
public class CustomerJpaRepositoryVerify {

    private final CustomerRepository repository;

    public void save(Customer entity){
        repository.save(entity);
    }

    public void saveNewCustomer(Customer entity) {
        if (repository.findByPhone(entity.getPhone()).isPresent()) {
            throw new CustomerException(DUPLICATE_PHONE);
        }
        repository.save(entity);
    }

    public Customer findById(Integer id) {
        Optional<Customer> customer = repository.findById(id);

        if(customer.isPresent()) {
            return customer.get();
        }
        throw new CustomerException(NOT_FOUNT);
    }

    public Customer findByPhone(String phone) {
        return repository.findByPhone(phone)
                .orElseThrow(() -> new CustomerException(NOT_FOUNT));
    }

    public Optional<Customer> findByPhoneOptional(String phone) {
        return repository.findByPhone(phone);
    }

    public Customer findByPhoneWithCoupons(String phone) {
        return repository.findByPhoneWithCoupons(phone)
                .orElseThrow(() -> new CustomerException(NOT_FOUNT));
    }

    public Customer findByPhoneWithAvailableCoupons(String phone) {
        return repository.findByPhoneWithCoupons(phone)
                .orElseThrow(() -> new CustomerException(NOT_FOUNT));
    }

    public List<Customer> readAll() {
        List<Customer> customer = repository.findAll();

        if(!customer.isEmpty()) {
            return customer;
        }

        throw new CustomerException(NOT_FOUNT);
    }

    public Page<Customer> readAll(Pageable pageable) {
        Page<Customer> customerPage = repository.findAll(pageable);

        if(customerPage.isEmpty()) {
            throw new CustomerException(NOT_FOUNT);
        }

        return customerPage;
    }

    public void delete(Customer customer) {
        repository.delete(customer);
    }

    public Customer update(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> searchCustomers(String keyword, String ageGroup) {
        return repository.searchCustomers(keyword, ageGroup);
    }

    public Page<Customer> searchCustomersPaged(String keyword, String ageGroup, Pageable pageable) {
        return repository.searchCustomersPaged(keyword, ageGroup, pageable);
    }

    public boolean existsByIdOrPhone(Integer id, String phone) {
        return repository.existsById(id) || repository.findByPhone(phone).isPresent();
    }
}
