package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.head_office.customer.exception.CustomerException;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.customer.exception.CustomerExceptionType.DUPLICATE_PHONE;
import static MeshX.HypeLink.head_office.customer.exception.CustomerExceptionType.NOT_FOUNT;
import static MeshX.HypeLink.head_office.promotion.exception.PromotionExceptionMessage.NOT_FOUND;


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
                .orElseThrow(() -> new CustomerException(NOT_FOUND));
    }

    public Customer findByPhoneWithCoupons(String phone) {
        return repository.findByPhoneWithCoupons(phone)
                .orElseThrow(() -> new CustomerException(NOT_FOUND));
    }

    public List<Customer> readAll() {
        List<Customer> customer = repository.findAll();

        if(!customer.isEmpty()) {
            return customer;
        }

        throw new CustomerException(NOT_FOUNT);
    }

    public void delete(Customer customer) {
        repository.delete(customer);
    }

    public Customer update(Customer customer) {
        return repository.save(customer);
    }
}
