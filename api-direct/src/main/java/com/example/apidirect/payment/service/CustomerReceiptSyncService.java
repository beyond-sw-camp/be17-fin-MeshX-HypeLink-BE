package com.example.apidirect.payment.service;

import com.example.apidirect.auth.adapter.out.external.MonolithSyncClient;
import com.example.apidirect.auth.adapter.out.external.dto.CustomerReceiptSyncDto;
import com.example.apidirect.payment.adapter.out.persistence.CustomerReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerReceiptSyncService {

    private final MonolithSyncClient monolithSyncClient;
    private final CustomerReceiptRepository customerReceiptRepository;

    /**
     * 모놀리스에서 CustomerReceipt 데이터를 페이징하여 동기화
     */
    @Transactional
    public int syncAllReceipts() {
        log.info("[SYNC] CustomerReceipt 전체 동기화 시작");

        int page = 0;
        int size = 300;
        int totalSynced = 0;
        Page<CustomerReceiptSyncDto> pageResult;

        do {
            try {
                pageResult = monolithSyncClient.getReceipts(page, size);
                List<CustomerReceiptSyncDto> receipts = pageResult.getContent();

                if (!receipts.isEmpty()) {
                    receipts.forEach(this::upsertReceipt);
                    totalSynced += receipts.size();
                    log.info("[SYNC] CustomerReceipt page {}/{} - {}개 처리 완료",
                            page + 1, pageResult.getTotalPages(), receipts.size());
                }

                page++;
            } catch (Exception e) {
                log.error("[SYNC] CustomerReceipt page {} 동기화 실패: {}", page, e.getMessage(), e);
                throw e;
            }
        } while (page < pageResult.getTotalPages());

        log.info("[SYNC] CustomerReceipt 전체 동기화 완료 - 총 {}개", totalSynced);
        return totalSynced;
    }

    /**
     * 단일 Receipt Upsert (Native Query 사용)
     */
    @Transactional
    public void upsertReceipt(CustomerReceiptSyncDto dto) {
        customerReceiptRepository.upsertReceipt(
                dto.getId(),
                dto.getPgProvider(),
                dto.getPgTid(),
                dto.getMerchantUid(),
                dto.getTotalAmount(),
                dto.getDiscountAmount(),
                dto.getCouponDiscount(),
                dto.getFinalAmount(),
                dto.getStoreId(),
                dto.getCustomerId(),
                dto.getMemberName(),
                dto.getMemberPhone(),
                null, // posCode - 모놀리스에는 없는 필드
                dto.getStatus(), // Enum을 String으로 저장
                dto.getPaidAt(),
                dto.getCancelledAt(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
