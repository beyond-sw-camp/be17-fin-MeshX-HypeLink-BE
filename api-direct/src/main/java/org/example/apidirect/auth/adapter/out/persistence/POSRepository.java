package org.example.apidirect.auth.adapter.out.persistence;

import org.example.apidirect.auth.adapter.out.entity.POSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface POSRepository extends JpaRepository<POSEntity, Integer> {
    Optional<POSEntity> findByPosCode(String posCode);
    Optional<POSEntity> findByMemberId(Integer memberId);

    @Modifying
    @Query(value = """
        INSERT INTO pos (id, pos_code, store_id, health_check, member_id, created_at, updated_at)
        VALUES (:id, :posCode, :storeId, :healthCheck, :memberId, :createdAt, :updatedAt)
        ON DUPLICATE KEY UPDATE
            pos_code = VALUES(pos_code),
            store_id = VALUES(store_id),
            health_check = VALUES(health_check),
            member_id = VALUES(member_id),
            updated_at = VALUES(updated_at)
        """, nativeQuery = true)
    void upsertPos(@Param("id") Integer id,
                   @Param("posCode") String posCode,
                   @Param("storeId") Integer storeId,
                   @Param("healthCheck") Boolean healthCheck,
                   @Param("memberId") Integer memberId,
                   @Param("createdAt") LocalDateTime createdAt,
                   @Param("updatedAt") LocalDateTime updatedAt);
}
