package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
    Optional<ColorEntity> findByColorName(String colorName);
}
