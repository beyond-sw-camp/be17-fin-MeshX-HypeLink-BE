package MeshX.HypeLink.head_office.customer.service;

import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.coupon.repository.CouponJpaRepositoryVerify;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerSignupReq;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerUpdateReq;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoListRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptListPagingRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptListRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptRes;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerCoupon;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import MeshX.HypeLink.head_office.customer.repository.CustomerJpaRepositoryVerify;
import MeshX.HypeLink.head_office.customer.repository.CustomerReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerJpaRepositoryVerify customerRepository;
    private final CouponJpaRepositoryVerify couponRepository;
    private final CustomerReceiptRepository receiptRepository;

    public void signup (CustomerSignupReq dto) {
        customerRepository.saveNewCustomer(dto.toEntity());
    }

    public CustomerInfoRes findById (Integer id) {
        Customer customer = customerRepository.findById(id);
        return CustomerInfoRes.toDto(customer);
    }

    public CustomerInfoListRes readAll (Pageable pageable) {
        Page<Customer> customerPage = customerRepository.readAll(pageable);
        List<CustomerInfoRes> customerInfoResList = CustomerInfoRes.toDtoList(customerPage.getContent());
        return CustomerInfoListRes.builder()
                .customerInfoResList(customerInfoResList)
                .totalPages(customerPage.getTotalPages())
                .totalElements(customerPage.getTotalElements())
                .currentPage(customerPage.getNumber())
                .pageSize(customerPage.getSize())
                .build();
    }

    public CustomerInfoRes update(CustomerUpdateReq dto) {
        Customer customer = customerRepository.findById(dto.getId());


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

    public CustomerInfoRes findByPhone(String phone) {
        Customer customer = customerRepository.findByPhoneWithCoupons(phone);
        return CustomerInfoRes.toDto(customer);
    }

    public CustomerInfoRes findByPhoneWithAvailableCoupons(String phone) {
        Customer customer = customerRepository.findByPhoneWithAvailableCoupons(phone);
        return CustomerInfoRes.toDto(customer);
    }

    // 매장별 주문 내역 조회
    public ReceiptListRes getReceiptsByStoreId(Integer storeId) {
        List<CustomerReceipt> receipts = receiptRepository.findByStoreIdOrderByPaidAtDesc(storeId);
        return ReceiptListRes.toDto(receipts);
    }

    // 매장별 주문 내역 조회 (페이징)
    public ReceiptListPagingRes getReceiptsByStoreId(Integer storeId, Pageable pageable) {
        Page<CustomerReceipt> receiptPage = receiptRepository.findByStoreIdOrderByPaidAtDesc(storeId, pageable);
        List<ReceiptRes> receiptResList = receiptPage.getContent().stream()
                .map(ReceiptRes::toDto)
                .collect(Collectors.toList());
        return ReceiptListPagingRes.builder()
                .receipts(receiptResList)
                .totalPages(receiptPage.getTotalPages())
                .totalElements(receiptPage.getTotalElements())
                .currentPage(receiptPage.getNumber())
                .pageSize(receiptPage.getSize())
                .build();
    }

    // 고객 검색 (키워드 + 연령대) - 페이징
    public CustomerInfoListRes searchCustomers(String keyword, String ageGroup, Pageable pageable) {
        Page<Customer> customerPage = customerRepository.searchCustomersPaged(keyword, ageGroup, pageable);
        List<CustomerInfoRes> customerInfoResList = CustomerInfoRes.toDtoList(customerPage.getContent());
        return CustomerInfoListRes.builder()
                .customerInfoResList(customerInfoResList)
                .totalPages(customerPage.getTotalPages())
                .totalElements(customerPage.getTotalElements())
                .currentPage(customerPage.getNumber())
                .pageSize(customerPage.getSize())
                .build();
    }
}
