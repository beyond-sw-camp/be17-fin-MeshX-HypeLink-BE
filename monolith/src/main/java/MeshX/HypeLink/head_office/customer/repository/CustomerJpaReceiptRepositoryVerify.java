package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerJpaReceiptRepositoryVerify {

    private final CustomerReceiptRepository receiptRepository;

    public void save(CustomerReceipt receipt) {
        receiptRepository.save(receipt);

    }
}
