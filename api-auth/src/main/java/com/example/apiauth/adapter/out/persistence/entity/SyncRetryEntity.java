package com.example.apiauth.adapter.out.persistence.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Builder
@Table(name = "sync_retry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SyncRetryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    private Integer entityId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String eventJson;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime failedAt;

    @Column(nullable = false)
    private LocalDateTime retryAt;

    @Column(nullable = false)
    private Integer retryCount;
}
