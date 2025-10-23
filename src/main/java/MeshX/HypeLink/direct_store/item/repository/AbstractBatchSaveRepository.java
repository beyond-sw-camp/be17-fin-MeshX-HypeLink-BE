package MeshX.HypeLink.direct_store.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * IN 쿼리 기반 중복 방지형 Batch Save 추상 클래스
 *
 * @param <T>  엔티티 타입
 * @param <K>  중복 비교 키 타입 (예: String, Integer 등)
 */
@Slf4j
public abstract class AbstractBatchSaveRepository<T, K> {

    /**
     * 엔티티에서 중복 비교용 키를 추출하는 메서드
     */
    protected abstract K extractKey(T entity);

    /**
     * DB에서 주어진 키 리스트에 해당하는 엔티티들의 키를 조회하는 메서드 (IN 쿼리)
     */
    protected abstract List<K> findExistingKeys(List<K> keys);

    /**
     * 실제 saveAll() 동작을 수행하는 메서드
     */
    protected abstract void saveAllToDb(List<T> entities);

    /**
     * DB에 없는 항목만 저장하는 메서드 (IN 쿼리 1회로 중복 제거)
     */
    @Transactional
    public void saveAllSkipDuplicate(List<T> entities) {
        if (entities == null || entities.isEmpty()) return;

        // 1️⃣ 모든 엔티티에서 중복 비교 키 추출
        List<K> allKeys = entities.stream()
                .map(this::extractKey)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (allKeys.isEmpty()) return;

        // 2️⃣ DB에 이미 존재하는 키 조회 (IN 쿼리 1회)
        List<K> existingKeys = findExistingKeys(allKeys);
        Set<K> existingKeySet = new HashSet<>(existingKeys);

        // 3️⃣ 신규 항목만 필터링
        List<T> newEntities = entities.stream()
                .filter(e -> !existingKeySet.contains(extractKey(e)))
                .toList();

        // 4️⃣ 저장 및 로그
        if (newEntities.isEmpty()) {
            log.info("[{} 저장 패스] 신규 항목 없음", getEntityName());
            return;
        }

        saveAllToDb(newEntities);
        log.info("[{} 저장 완료] {}건 신규 저장됨", getEntityName(), newEntities.size());
    }

    protected String getEntityName() {
        return this.getClass().getSimpleName().replace("RepositoryVerify", "");
    }
}
