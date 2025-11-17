package com.example.apidirect.customer.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.customer.adapter.out.kafka.CustomerSyncProducer;
import com.example.apidirect.customer.adapter.out.kafka.dto.CustomerSyncEvent;
import com.example.apidirect.customer.common.CustomerException;
import com.example.apidirect.customer.domain.Customer;
import com.example.apidirect.customer.usecase.port.in.CustomerCommandPort;
import com.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import com.example.apidirect.customer.usecase.port.in.request.CustomerUpdateCommand;
import com.example.apidirect.customer.usecase.port.out.CustomerPersistencePort;
import com.example.apidirect.customer.usecase.port.out.mapper.CustomerResponseMapper;
import com.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.apidirect.customer.common.CustomerExceptionType.*;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class CustomerCommandUseCase implements CustomerCommandPort {

    private final CustomerPersistencePort customerPersistencePort;
    private final CustomerSyncProducer customerSyncProducer;

    @Override
    public CustomerResponse signup(CustomerSignupCommand command) {
        log.info("고객 회원가입 시작 - phone: {}", command.getPhone());

        // 1. 로컬 DB에서 중복 확인
        if (customerPersistencePort.existsByPhone(command.getPhone())) {
            log.warn("이미 등록된 전화번호 - phone: {}", command.getPhone());
            throw new CustomerException(CUSTOMER_ALREADY_EXISTS);
        }

        // 2. 로컬 DB에 Customer 생성
        LocalDateTime now = LocalDateTime.now();
        Customer newCustomer = Customer.builder()
                .name(command.getName())
                .phone(command.getPhone())
                .birthDate(command.getBirthDate())
                .createdAt(now)
                .updatedAt(now)
                .build();

        Customer savedCustomer = customerPersistencePort.save(newCustomer);
        log.info("로컬 Customer 저장 완료 - id: {}, phone: {}", savedCustomer.getId(), savedCustomer.getPhone());

        // 3. Kafka를 통해 본사에 신규 Customer 정보 전송
        try {
            CustomerSyncEvent event = CustomerSyncEvent.builder()
                    .id(savedCustomer.getId())
                    .name(savedCustomer.getName())
                    .phone(savedCustomer.getPhone())
                    .birthDate(savedCustomer.getBirthDate())
                    .createdAt(savedCustomer.getCreatedAt())
                    .updatedAt(savedCustomer.getUpdatedAt())
                    .build();

            customerSyncProducer.sendCustomerSync(event);
            log.info("신규 Customer 본사 동기화 이벤트 발송 완료 - id: {}", savedCustomer.getId());
        } catch (Exception e) {
            log.error("신규 Customer 본사 동기화 실패 - id: {}, error: {}",
                    savedCustomer.getId(), e.getMessage(), e);
            // Kafka 전송 실패해도 예외 던지지 않음 (회원가입은 완료)
        }

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
