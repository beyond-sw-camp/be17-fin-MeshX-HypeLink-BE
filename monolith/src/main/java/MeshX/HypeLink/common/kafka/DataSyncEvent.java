package MeshX.HypeLink.common.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSyncEvent {
    private SyncOperation operation;
    private EntityType entityType;
    private Integer entityId;
    private String entityData;
    private LocalDateTime timestamp;

    public enum SyncOperation {
        CREATE, UPDATE, DELETE
    }

    public enum EntityType {
        MEMBER, STORE, POS, DRIVER
    }
}
