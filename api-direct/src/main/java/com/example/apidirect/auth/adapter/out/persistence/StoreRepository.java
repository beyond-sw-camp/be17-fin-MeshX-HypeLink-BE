package com.example.apidirect.auth.adapter.out.persistence;

import com.example.apidirect.auth.adapter.out.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {
    Optional<StoreEntity> findByStoreNumber(String storeNumber);

    @Modifying
    @Query(value = """
        INSERT INTO store (id, lat, lon, pos_count, store_number, store_state, member_id, created_at, updated_at)
        VALUES (:id, :lat, :lon, :posCount, :storeNumber, :storeState, :memberId, :createdAt, :updatedAt)
        ON DUPLICATE KEY UPDATE
            lat = VALUES(lat),
            lon = VALUES(lon),
            pos_count = VALUES(pos_count),
            store_number = VALUES(store_number),
            store_state = VALUES(store_state),
            member_id = VALUES(member_id),
            updated_at = VALUES(updated_at)
        """, nativeQuery = true)
    void upsertStore(@Param("id") Integer id,
                     @Param("lat") Double lat,
                     @Param("lon") Double lon,
                     @Param("posCount") Integer posCount,
                     @Param("storeNumber") String storeNumber,
                     @Param("storeState") String storeState,
                     @Param("memberId") Integer memberId,
                     @Param("createdAt") LocalDateTime createdAt,
                     @Param("updatedAt") LocalDateTime updatedAt);
}
