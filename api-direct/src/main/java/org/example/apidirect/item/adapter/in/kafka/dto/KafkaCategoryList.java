package org.example.apidirect.item.adapter.in.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaCategoryList {
    private List<KafkaCategory> categories;
}
