package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Color {
    private Integer id;
    private String colorName;
    private String colorCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
