package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
    Optional<SizeEntity> findBySize(String size);
}
