package com.example.apinotice.notice.adaptor.out.jpa;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeImageEntity> images = new ArrayList<>();

    public void addImageEntity(NoticeImageEntity image) {
        this.images.add(image);
        image.connectNotice(this);
    }

    public void clearImages() {
        this.images.clear();
    }
}
