package com.example.apiitem.item.usecase.port.in;

import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaCategoryList;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaColorList;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaItemCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaSizeList;

public interface KafkaItemInPort {
    void save(KafkaItemCommand dto);
    void saveSizes(KafkaSizeList command);
    void saveCategories(KafkaCategoryList command);
    void saveColor(KafkaColorList command);
    void rollback(Integer id);
}
