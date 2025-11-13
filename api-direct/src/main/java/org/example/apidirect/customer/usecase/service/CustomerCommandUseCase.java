package org.example.apidirect.customer.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.customer.common.CustomerException;
import org.example.apidirect.customer.domain.Customer;
import org.example.apidirect.customer.usecase.port.in.CustomerCommandPort;
import org.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import org.example.apidirect.customer.usecase.port.in.request.CustomerUpdateCommand;
import org.example.apidirect.customer.usecase.port.out.CustomerPersistencePort;
import org.example.apidirect.customer.usecase.port.out.HeadOfficeCustomerFeignPort;
import org.example.apidirect.customer.usecase.port.out.mapper.CustomerResponseMapper;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.springframework.transaction.annotation.Transactional;

import static org.example.apidirect.customer.common.CustomerExceptionType.*;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class CustomerCommandUseCase implements CustomerCommandPort {

    private final CustomerPersistencePort customerPersistencePort;
    private final HeadOfficeCustomerFeignPort headOfficeCustomerFeignPort;

    @Override
    public CustomerResponse signup(CustomerSignupCommand command) {
        log.info("고객 회원가입 시작 - phone: {}", command.getPhone());

        // 1. 로컬 DB에서 중복 확인
        if (customerPersistencePort.existsByPhone(command.getPhone())) {
            log.warn("이미 등록된 전화번호 - phone: {}", command.getPhone());
            throw new CustomerException(CUSTOMER_ALREADY_EXISTS);
        }

        // 2. 본사 API 호출하여 Customer 생성
        log.info("본사 Customer API 호출 시작 - phone: {}", command.getPhone());
        CustomerResponse headOfficeResponse = headOfficeCustomerFeignPort.createCustomer(command);

        if (headOfficeResponse == null) {
            log.error("본사 Customer 생성 실패 - phone: {}", command.getPhone());
            throw new CustomerException(HEAD_OFFICE_API_ERROR);
        }

        log.info("본사 Customer 생성 완료 - id: {}, phone: {}",
                headOfficeResponse.getId(), headOfficeResponse.getPhone());

        // 3. 본사에서 받은 정보를 로컬 DB에 저장
        Customer localCustomer = CustomerResponseMapper.toDomain(headOfficeResponse);
        Customer savedCustomer = customerPersistencePort.save(localCustomer);

        log.info("로컬 Customer 저장 완료 - id: {}", savedCustomer.getId());

        return CustomerResponseMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse update(CustomerUpdateCommand command) {
        log.info("고객 정보 수정 시작 - id: {}", command.getId());

        // 로컬 DB에서 Customer 조회
        Customer customer = customerPersistencePort.findById(command.getId())
                .orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND));

        // Customer Entity 수정 (JPA dirty checking)
        Customer updatedCustomer = Customer.builder()
                .id(customer.getId())
                .name(command.getName() != null ? command.getName() : customer.getName())
                .phone(command.getPhone() != null ? command.getPhone() : customer.getPhone())
                .birthDate(customer.getBirthDate())
                .build();

        Customer saved = customerPersistencePort.save(updatedCustomer);

        log.info("고객 정보 수정 완료 - id: {}", saved.getId());

        return CustomerResponseMapper.toResponse(saved);
    }
}
