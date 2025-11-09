package org.example.apidirect.item.adapter.in.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaItemImageDto {
    private Integer id;
    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;
}
