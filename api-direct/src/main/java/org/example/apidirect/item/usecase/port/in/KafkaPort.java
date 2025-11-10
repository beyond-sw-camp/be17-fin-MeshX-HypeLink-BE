package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.usecase.port.in.request.kafka.KafkaCategoryListCommand;
import org.example.apidirect.item.usecase.port.in.request.kafka.KafkaColorListCommand;
import org.example.apidirect.item.usecase.port.in.request.kafka.KafkaItemCommand;
import org.example.apidirect.item.usecase.port.in.request.kafka.KafkaSizeListCommand;

public interface KafkaPort {
    void saveItem(KafkaItemCommand command);

    void saveCategories(KafkaCategoryListCommand command);

    void saveColors(KafkaColorListCommand command);

    void saveSizes(KafkaSizeListCommand command);
}
