package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Size {
    private Integer id;
    private String size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
