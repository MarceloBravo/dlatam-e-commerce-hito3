package com.mabc.domain.repository;

import com.mabc.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Long id);

    List<Category> findAll();

    List<Category> findAllByIds(List<Long> ids);

    Category save(Category category);

    void deleteById(Long id);

    boolean existsById(Long id);
}
