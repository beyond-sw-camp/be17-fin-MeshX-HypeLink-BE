package MeshX.HypeLink.head_office.customer.service;

import MeshX.HypeLink.head_office.customer.kafka.dto.CustomerReceiptSyncEvent;
import MeshX.HypeLink.head_office.customer.kafka.producer.CustomerReceiptSyncProducer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import MeshX.HypeLink.head_office.customer.repository.CustomerReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerReceiptSyncService {

    private final CustomerReceiptRepository customerReceiptRepository;
    private final CustomerReceiptSyncProducer customerReceiptSyncProducer;

    /**
     * 전체 영수증 데이터를 Direct로 동기화
     */
    @Transactional(readOnly = true)
    public void syncAllReceipts() {
        log.info("[SYNC] 전체 영수증 조회 시작");

        List<CustomerReceipt> receipts = customerReceiptRepository.findAll();

        if (receipts.isEmpty()) {
            log.info("[SYNC] 동기화할 영수증이 없습니다.");
            return;
        }

        List<CustomerReceiptSyncEvent> events = receipts.stream()
                .map(CustomerReceiptSyncEvent::from)
                .collect(Collectors.toList());

        customerReceiptSyncProducer.sendBulkReceiptSync(events);

        log.info("[SYNC] 전체 영수증 동기화 완료 - count: {}", events.size());
    }
}
