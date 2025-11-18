package MeshX.HypeLink.head_office.customer.event;

import MeshX.HypeLink.head_office.customer.service.CustomerReceiptSyncService;
import MeshX.HypeLink.head_office.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartupListener {

    private final CustomerService customerService;
    private final CustomerReceiptSyncService customerReceiptSyncService;

    /**
     * 서버 시작 완료 후 전체 Customer를 모든 가맹점에게 전송
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            log.info("[STARTUP] 서버 시작 완료 - 전체 Customer 동기화 시작");
            customerService.syncAllCustomersToKafka();
            log.info("[STARTUP] 전체 Customer 동기화 완료");
        } catch (Exception e) {
            log.error("[STARTUP] Customer 동기화 실패 - error: {}", e.getMessage(), e);
        }

        try {
            log.info("[STARTUP] 전체 CustomerReceipt 동기화 시작");
            customerReceiptSyncService.syncAllReceipts();
            log.info("[STARTUP] 전체 CustomerReceipt 동기화 완료");
        } catch (Exception e) {
            log.error("[STARTUP] CustomerReceipt 동기화 실패 - error: {}", e.getMessage(), e);
        }
    }
}
