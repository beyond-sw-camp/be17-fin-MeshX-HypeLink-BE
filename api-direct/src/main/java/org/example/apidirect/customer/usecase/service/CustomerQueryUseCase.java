package org.example.apidirect.customer.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.customer.common.CustomerException;
import org.example.apidirect.customer.domain.Customer;
import org.example.apidirect.customer.usecase.port.in.CustomerQueryPort;
import org.example.apidirect.customer.usecase.port.out.CustomerPersistencePort;
import org.example.apidirect.customer.usecase.port.out.HeadOfficeCustomerFeignPort;
import org.example.apidirect.customer.usecase.port.out.mapper.CustomerResponseMapper;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.example.apidirect.customer.common.CustomerExceptionType.*;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerQueryUseCase implements CustomerQueryPort {

    private final CustomerPersistencePort customerPersistencePort;
    private final HeadOfficeCustomerFeignPort headOfficeCustomerFeignPort;

    @Override
    public CustomerResponse findById(Integer id) {
        log.info("고객 조회 시작 - id: {}", id);

        Customer customer = customerPersistencePort.findById(id)
                .orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND));

        return CustomerResponseMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse findByPhone(String phone) {
        log.info("고객 조회 시작 - phone: {}", phone);

        // 1. 로컬 DB에서 조회
        Optional<Customer> localCustomer = customerPersistencePort.findByPhone(phone);
        if (localCustomer.isPresent()) {
            log.info("로컬 DB에서 Customer 조회 완료 - id: {}", localCustomer.get().getId());
            return CustomerResponseMapper.toResponse(localCustomer.get());
        }

        log.info("로컬 DB에 Customer 없음, 본사 API 호출 시작 - phone: {}", phone);

        // 2. 본사 API에서 조회
        CustomerResponse headOfficeResponse = headOfficeCustomerFeignPort.getCustomerByPhone(phone);

        if (headOfficeResponse == null) {
            log.warn("본사에도 Customer 없음 - phone: {}", phone);
            throw new CustomerException(CUSTOMER_NOT_FOUND);
        }

        log.info("본사에서 Customer 조회 완료 - id: {}, phone: {}",
                headOfficeResponse.getId(), headOfficeResponse.getPhone());

        // 3. 본사에서 받은 정보를 로컬 DB에 저장 (캐싱)
        Customer customer = CustomerResponseMapper.toDomain(headOfficeResponse);
        Customer savedCustomer = customerPersistencePort.save(customer);

        log.info("본사 Customer를 로컬 DB에 저장 완료 - id: {}", savedCustomer.getId());

        return CustomerResponseMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse findByPhoneWithAvailableCoupons(String phone) {
        log.info("고객 + 사용가능 쿠폰 조회 시작 - phone: {}", phone);

        // 본사 API에서 Customer + 쿠폰 정보 함께 조회
        CustomerResponse response = headOfficeCustomerFeignPort.getCustomerWithAvailableCoupons(phone);

        if (response == null) {
            log.warn("본사에 Customer 없음 - phone: {}", phone);
            throw new CustomerException(CUSTOMER_NOT_FOUND);
        }

        log.info("본사에서 Customer + 쿠폰 정보 조회 완료 - id: {}, phone: {}, 쿠폰 개수: {}",
                response.getId(), response.getPhone(),
                response.getAvailableCoupons() != null ? response.getAvailableCoupons().size() : 0);

        return response;
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
    @Transactional
    public Customer getCustomerForPayment(Integer customerId) {
        log.info("결제용 고객 조회 시작 - customerId: {}", customerId);

        // 1. 로컬 DB에서 조회
        Optional<Customer> localCustomer = customerPersistencePort.findById(customerId);
        if (localCustomer.isPresent()) {
            log.info("로컬 DB에서 Customer 조회 완료 - id: {}", localCustomer.get().getId());
            return localCustomer.get();
        }

        log.info("로컬 DB에 Customer 없음, 본사 API 호출 시작 - customerId: {}", customerId);

        // 2. 본사 API에서 조회
        CustomerResponse headOfficeResponse = headOfficeCustomerFeignPort.getCustomerById(customerId);

        if (headOfficeResponse == null) {
            log.warn("본사에도 Customer 없음 - customerId: {}", customerId);
            throw new CustomerException(CUSTOMER_NOT_FOUND);
        }

        log.info("본사에서 Customer 조회 완료 - id: {}", headOfficeResponse.getId());

        // 3. 본사에서 받은 정보를 로컬 DB에 저장 (캐싱)
        Customer customer = CustomerResponseMapper.toDomain(headOfficeResponse);
        Customer savedCustomer = customerPersistencePort.save(customer);

        log.info("본사 Customer를 로컬 DB에 저장 완료 - id: {}", savedCustomer.getId());

        return savedCustomer;
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
