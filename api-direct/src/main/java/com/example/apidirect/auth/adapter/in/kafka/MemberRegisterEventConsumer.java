package com.example.apidirect.auth.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.auth.domain.POS;
import com.example.apidirect.auth.domain.Store;
import com.example.apidirect.auth.usecase.port.out.POSPersistencePort;
import com.example.apidirect.auth.usecase.port.out.StorePersistencePort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberRegisterEventConsumer {

    private final StorePersistencePort storePersistencePort;
    private final POSPersistencePort posPersistencePort;

//    @KafkaListener(topics = "member-registered", groupId = "api-direct-consumer")
//    @Transactional
    public void consumeMemberRegisterEvent(MemberRegisterEvent event) {
        try {
            log.info("[KAFKA] MemberRegisterEvent 수신 - memberId={}, role={}",
                    event.getMemberId(), event.getRole());

            // Store/POS 정보만 저장 (Member, Driver 불필요)
            switch (event.getRole()) {
                case BRANCH_MANAGER:
                    if (event.getStoreInfo() != null) {
                        saveStore(event.getStoreInfo(), event.getMemberId());
                    }
                    break;
                case POS_MEMBER:
                    if (event.getPosInfo() != null) {
                        savePOS(event.getPosInfo(), event.getMemberId());
                    }
                    break;
                default:
                    log.info("[KAFKA] Store/POS 정보 없음 - role={}", event.getRole());
                    break;
            }

        } catch (Exception e) {
            log.error("[KAFKA] MemberRegisterEvent 처리 실패 - memberId={}, error={}",
                    event.getMemberId(), e.getMessage(), e);
        }
    }

    private void saveStore(MemberRegisterEvent.StoreInfo storeInfo, Integer memberId) {
        Store store = Store.builder()
                .id(storeInfo.getStoreId())
                .lat(storeInfo.getLat() != null ? storeInfo.getLat() : 0.0)
                .lon(storeInfo.getLon() != null ? storeInfo.getLon() : 0.0)
                .posCount(storeInfo.getPosCount() != null ? storeInfo.getPosCount() : 0)
                .storeNumber(storeInfo.getStoreNumber())
                .storeState(storeInfo.getStoreState() != null ? storeInfo.getStoreState().name() : null)
                .memberId(memberId)
                .createdAt(storeInfo.getCreatedAt())
                .updatedAt(storeInfo.getUpdatedAt())
                .build();

        storePersistencePort.save(store);
        log.info("[KAFKA] Store 저장 완료 - storeId={}, storeNumber={}",
                storeInfo.getStoreId(), storeInfo.getStoreNumber());
    }

    private void savePOS(MemberRegisterEvent.PosInfo posInfo, Integer memberId) {
        POS pos = POS.builder()
                .id(posInfo.getPosId())
                .posCode(posInfo.getPosCode())
                .storeId(posInfo.getStoreId())
                .healthCheck(posInfo.getHealthCheck() != null ? posInfo.getHealthCheck() : false)
                .memberId(memberId)
                .createdAt(posInfo.getCreatedAt())
                .updatedAt(posInfo.getUpdatedAt())
                .build();

        posPersistencePort.save(pos);
        log.info("[KAFKA] POS 저장 완료 - posId={}, posCode={}",
                posInfo.getPosId(), posInfo.getPosCode());
    }
}
