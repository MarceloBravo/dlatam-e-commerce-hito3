package com.mabc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mabc.entities.Mark;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    // This interface is intended to handle data persistence for marks.
}