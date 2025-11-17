package com.example.apidirect.auth.adapter.out.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sync_status")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SyncStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String syncType;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    private LocalDateTime syncedAt;

    @Builder
    public SyncStatusEntity(String syncType, Boolean completed, LocalDateTime syncedAt) {
        this.syncType = syncType;
        this.completed = completed;
        this.syncedAt = syncedAt;
    }

    public void markAsCompleted() {
        this.completed = true;
        this.syncedAt = LocalDateTime.now();
    }
}
