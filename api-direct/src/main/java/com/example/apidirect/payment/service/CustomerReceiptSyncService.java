package com.example.apidirect.payment.service;

import com.example.apidirect.auth.adapter.out.external.MonolithSyncClient;
import com.example.apidirect.auth.adapter.out.external.dto.CustomerReceiptSyncDto;
import com.example.apidirect.payment.adapter.out.entity.CustomerReceiptEntity;
import com.example.apidirect.payment.adapter.out.persistence.CustomerReceiptRepository;
import com.example.apidirect.payment.domain.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerReceiptSyncService {

    private final MonolithSyncClient monolithSyncClient;
    private final CustomerReceiptRepository customerReceiptRepository;

    /**
     * 모놀리스에서 CustomerReceipt 데이터를 비동기로 페이징하여 동기화
     */
    @Async
    @Transactional
    public void syncAllReceipts() {
        log.info("[SYNC] CustomerReceipt 전체 동기화 시작");

        int page = 0;
        int size = 300;
        Page<CustomerReceiptSyncDto> pageResult;

        do {
            try {
                pageResult = monolithSyncClient.getReceipts(page, size);
                List<CustomerReceiptSyncDto> receipts = pageResult.getContent();

                if (!receipts.isEmpty()) {
                    receipts.forEach(this::upsertReceipt);
                    log.info("[SYNC] CustomerReceipt page {}/{} - {}개 처리 완료",
                            page + 1, pageResult.getTotalPages(), receipts.size());
                }

                page++;
            } catch (Exception e) {
                log.error("[SYNC] CustomerReceipt page {} 동기화 실패: {}", page, e.getMessage(), e);
                throw e;
            }
        } while (page < pageResult.getTotalPages());

        log.info("[SYNC] CustomerReceipt 전체 동기화 완료");
    }

    /**
     * 단일 Receipt Upsert (ID 기반으로 존재하면 업데이트, 없으면 생성)
     */
    @Transactional
    public void upsertReceipt(CustomerReceiptSyncDto dto) {
        Optional<CustomerReceiptEntity> existing = customerReceiptRepository.findById(dto.getId());

        if (existing.isPresent()) {
            // 기존 데이터가 있으면 업데이트 (기존 엔티티 삭제 후 새로 생성)
            customerReceiptRepository.deleteById(dto.getId());
            customerReceiptRepository.flush();
        }

        // 새로운 엔티티 생성
        CustomerReceiptEntity entity = CustomerReceiptEntity.builder()
                .id(dto.getId())
                .pgProvider(dto.getPgProvider())
                .pgTid(dto.getPgTid())
                .merchantUid(dto.getMerchantUid())
                .totalAmount(dto.getTotalAmount())
                .discountAmount(dto.getDiscountAmount())
                .couponDiscount(dto.getCouponDiscount())
                .finalAmount(dto.getFinalAmount())
                .storeId(dto.getStoreId())
                .customerId(dto.getCustomerId())
                .memberName(dto.getMemberName())
                .memberPhone(dto.getMemberPhone())
                .posCode(null) // 모놀리스에는 없는 필드
                .status(mapPaymentStatus(dto.getStatus()))
                .paidAt(dto.getPaidAt())
                .build();

        customerReceiptRepository.save(entity);
    }

    /**
     * 모놀리스의 PaymentStatus를 api-direct의 PaymentStatus로 매핑
     */
    private PaymentStatus mapPaymentStatus(String status) {
        if (status == null) {
            return PaymentStatus.PENDING;
        }

        return switch (status) {
            case "READY" -> PaymentStatus.PENDING;
            case "PAID" -> PaymentStatus.PAID;
            case "CANCELLED" -> PaymentStatus.CANCELLED;
            case "FAILED" -> PaymentStatus.FAILED;
            default -> {
                log.warn("Unknown payment status from monolith: {}, defaulting to PENDING", status);
                yield PaymentStatus.PENDING;
            }
        };
    }
}
