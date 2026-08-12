package com.mabc.infrastructure.persistence.spring;

import com.mabc.infrastructure.persistence.entity.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkJpaRepository extends JpaRepository<MarkEntity, Long> {
}
