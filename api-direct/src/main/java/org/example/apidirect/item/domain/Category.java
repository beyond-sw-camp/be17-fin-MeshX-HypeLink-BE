package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    private Integer id;
    private String category;
}
