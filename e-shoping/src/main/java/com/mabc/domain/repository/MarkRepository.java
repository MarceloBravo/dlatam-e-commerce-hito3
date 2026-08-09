package com.mabc.domain.repository;

import com.mabc.domain.entity.Mark;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {

    Optional<Mark> findById(Long id);

    List<Mark> findAll();

    Mark save(Mark mark);

    void deleteById(Long id);

    boolean existsById(Long id);
}
