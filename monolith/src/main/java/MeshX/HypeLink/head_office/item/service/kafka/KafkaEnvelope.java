package MeshX.HypeLink.head_office.item.service.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaEnvelope<T> {
    private String type;   // ex) "ITEM", "STORE", "ORDER"
    private T payload;     // 실제 DTO
}