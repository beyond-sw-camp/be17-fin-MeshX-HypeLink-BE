package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Category {
    private Integer id;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
