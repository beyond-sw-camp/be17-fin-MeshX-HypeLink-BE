package org.example.apidirect.item.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "size")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SizeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String size;

    @Builder
    private SizeEntity(Integer id, String size) {
        this.id = id;
        this.size = size;
    }
}
