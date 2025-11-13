package org.example.apidirect.item.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "color")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String colorName;

    @Column(nullable = false)
    private String colorCode;

    @Builder
    private ColorEntity(Integer id, String colorName, String colorCode) {
        this.id = id;
        this.colorName = colorName;
        this.colorCode = colorCode;
    }
}
