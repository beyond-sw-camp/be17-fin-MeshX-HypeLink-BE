package MeshX.HypeLink.direct_store.payment.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaEnvelope<T> {
    private String type;
    private T payload;
}
