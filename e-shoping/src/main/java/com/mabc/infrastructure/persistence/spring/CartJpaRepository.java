package com.mabc.infrastructure.persistence.spring;

import com.mabc.infrastructure.persistence.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findTopByOrderByIdDesc();
}
