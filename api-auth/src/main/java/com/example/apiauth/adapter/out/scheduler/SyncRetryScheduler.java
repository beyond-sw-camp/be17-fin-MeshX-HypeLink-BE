package com.example.apiauth.adapter.out.scheduler;

import com.example.apiauth.adapter.out.persistence.SyncRetryJpaRepository;
import com.example.apiauth.adapter.out.persistence.entity.SyncRetryEntity;
import com.example.apiauth.adapter.out.persistence.mapper.SyncRetryMapper;
import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.domain.model.SyncRetry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SyncRetryScheduler {

    private final SyncRetryJpaRepository syncRetryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String MEMBER_REGISTERED_TOPIC = "member-registered";
    private static final int MAX_RETRY_COUNT = 10;

    @Scheduled(fixedDelay = 300000) // 5분마다 실행
    @Transactional
    public void retryFailedSync() {
        log.info("동기화 재시도 스케줄러 시작");

        try {
            List<SyncRetryEntity> retryTargets = syncRetryRepository.findAllRetryTargets(LocalDateTime.now());

            if (retryTargets.isEmpty()) {
                log.debug("재시도 대상 없음");
                return;
            }

            log.info("재시도 대상 {}건 발견", retryTargets.size());

            for (SyncRetryEntity entity : retryTargets) {
                processRetry(entity);
            }

            log.info("동기화 재시도 스케줄러 완료");

        } catch (Exception e) {
            log.error("동기화 재시도 스케줄러 실행 중 오류 발생", e);
        }
    }

    private void processRetry(SyncRetryEntity entity) {
        SyncRetry syncRetry = SyncRetryMapper.toDomain(entity);

        // 최대 재시도 횟수 초과 시 삭제
        if (syncRetry.getRetryCount() >= MAX_RETRY_COUNT) {
            log.warn("최대 재시도 횟수 초과, entityId={} 삭제", syncRetry.getEntityId());
            syncRetryRepository.delete(entity);
            return;
        }

        try {
            MemberRegisterEvent event = objectMapper.readValue(syncRetry.getEventJson(), MemberRegisterEvent.class);
            kafkaTemplate.send(MEMBER_REGISTERED_TOPIC, event.getMemberId().toString(), event);
            // 카프카 다시 보내기

            log.info("회원가입 이벤트 재발행 완료: memberId={}, 재시도 횟수={}",
                    event.getMemberId(), syncRetry.getRetryCount());

            // 재시도 횟수 증가 및 다음 재시도 시간 업데이트
            SyncRetry updatedRetry = syncRetry.increaseRetryCount();
            SyncRetryEntity updatedEntity = SyncRetryMapper.toEntity(updatedRetry);
            syncRetryRepository.save(updatedEntity);

        } catch (JsonProcessingException e) {
            log.error("이벤트 JSON 역직렬화 실패, entityId={} 삭제", syncRetry.getEntityId(), e);
            syncRetryRepository.delete(entity); // JSON 파싱 실패 시 삭제
        } catch (Exception e) {
            log.error("이벤트 재발행 실패, entityId={}", syncRetry.getEntityId(), e);
            // 재시도 횟수만 증가
            SyncRetry updatedRetry = syncRetry.increaseRetryCount();
            SyncRetryEntity updatedEntity = SyncRetryMapper.toEntity(updatedRetry);
            syncRetryRepository.save(updatedEntity);
        }
    }
}
