package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.adapter.in.kafka.dto.KafkaCategoryList;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaColorList;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemDto;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaSizeList;

public interface KafkaPort {
    void saveItem(KafkaItemDto dto);

    void saveCategories(KafkaCategoryList dto);

    void saveColors(KafkaColorList dto);

    void saveSizes(KafkaSizeList dto);
}
