package com.example.apiauth.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "sync_status")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SyncStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String entityType; // MEMBER, STORE, POS, DRIVER

    @Column(nullable = false)
    private LocalDateTime lastSyncAt;

    @Column(nullable = false)
    private Integer totalSyncedCount;

    @Column(nullable = false)
    private Boolean isInitialSyncCompleted;

    public void updateSyncStatus(LocalDateTime lastSyncAt, Integer totalSyncedCount) {
        this.lastSyncAt = lastSyncAt;
        this.totalSyncedCount = totalSyncedCount;
        this.isInitialSyncCompleted = true;
    }
}
