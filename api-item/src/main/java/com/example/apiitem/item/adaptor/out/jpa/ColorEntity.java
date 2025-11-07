package com.example.apiitem.item.adaptor.out.jpa;

import MeshX.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String colorName;
    private String colorCode;

    @Builder
    private ColorEntity(Integer id, String colorName, String colorCode) {
        this.id = id;
        this.colorName = colorName;
        this.colorCode = colorCode;
    }
}
