package com.example.apidirect.customer.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.customer.common.CustomerException;
import com.example.apidirect.customer.domain.Customer;
import com.example.apidirect.customer.usecase.port.in.CustomerQueryPort;
import com.example.apidirect.customer.usecase.port.out.CustomerPersistencePort;
import com.example.apidirect.customer.usecase.port.out.mapper.CustomerResponseMapper;
import com.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static com.example.apidirect.customer.common.CustomerExceptionType.*;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerQueryUseCase implements CustomerQueryPort {

    private final CustomerPersistencePort customerPersistencePort;

    @Override
    public CustomerResponse findById(Integer id) {
        log.info("고객 조회 시작 - id: {}", id);

        Customer customer = customerPersistencePort.findById(id)
                .orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND));

        return CustomerResponseMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse findByPhone(String phone) {
        log.info("고객 조회 시작 - phone: {}", phone);

        // 로컬 DB에서만 조회
        Customer customer = customerPersistencePort.findByPhone(phone)
                .orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND));

        log.info("로컬 DB에서 Customer 조회 완료 - id: {}", customer.getId());

        return CustomerResponseMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse findByPhoneWithAvailableCoupons(String phone) {
        log.info("고객 + 사용가능 쿠폰 조회 시작 - phone: {}", phone);

        // 로컬 DB에서만 조회 (쿠폰 정보는 본사에만 있으므로 제외)
        Customer customer = customerPersistencePort.findByPhone(phone)
                .orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND));

        log.info("로컬 DB에서 Customer 조회 완료 - id: {}, phone: {}", customer.getId(), customer.getPhone());

        // 쿠폰 정보는 본사 시스템에서 별도 조회 필요 (Feign 제거로 인해 쿠폰 정보 없음)
        // 필요시 별도 API 호출하거나 프론트엔드에서 본사 API를 직접 호출하도록 변경
        return CustomerResponseMapper.toResponse(customer);
    }

    @Override
    public Page<CustomerResponse> searchCustomers(String keyword, String ageGroup, Pageable pageable) {
        log.info("고객 검색 시작 - keyword: {}, ageGroup: {}", keyword, ageGroup);

        Page<Customer> customers;

        if (ageGroup != null && !ageGroup.equals("all")) {
            // 연령대별 검색
            int[] ageRange = getAgeRange(ageGroup);
            customers = customerPersistencePort.findByAgeRange(ageRange[0], ageRange[1], pageable);
        } else if (keyword != null && !keyword.isBlank()) {
            // 키워드 검색
            customers = customerPersistencePort.findByKeyword(keyword, pageable);
        } else {
            // 전체 조회
            customers = customerPersistencePort.findAll(pageable);
        }

        return customers.map(CustomerResponseMapper::toResponse);
    }

    @Override
    public Page<CustomerResponse> findAll(Pageable pageable) {
        log.info("전체 고객 조회 시작");
        Page<Customer> customers = customerPersistencePort.findAll(pageable);
        return customers.map(CustomerResponseMapper::toResponse);
    }

    @Override
    public Customer getCustomerForPayment(Integer customerId) {
        log.info("결제용 고객 조회 시작 - customerId: {}", customerId);

        // 로컬 DB에서만 조회
        Customer customer = customerPersistencePort.findById(customerId)
                .orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND));

        log.info("로컬 DB에서 Customer 조회 완료 - id: {}", customer.getId());

        return customer;
    }

    private int[] getAgeRange(String ageGroup) {
        return switch (ageGroup) {
            case "10s" -> new int[]{10, 20};
            case "20s" -> new int[]{20, 30};
            case "30s" -> new int[]{30, 40};
            case "40s" -> new int[]{40, 50};
            case "50s" -> new int[]{50, 60};
            case "60s" -> new int[]{60, 150};
            default -> new int[]{0, 150};
        };
    }
}
