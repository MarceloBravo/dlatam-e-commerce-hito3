package com.mabc.infrastructure.persistence.jpa;

import com.mabc.domain.entity.Category;
import com.mabc.domain.repository.CategoryRepository;
import com.mabc.domain.valueobject.Name;
import com.mabc.infrastructure.persistence.entity.CategoryEntity;
import com.mabc.infrastructure.persistence.spring.CategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCategoryRepository implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public JpaCategoryRepository(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Category> findAllByIds(List<Long> ids) {
        return categoryJpaRepository.findAllById(ids).stream().map(this::toDomain).toList();
    }

    @Override
    public Category save(Category category) {
        CategoryEntity saved = categoryJpaRepository.save(toEntity(category));
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryJpaRepository.existsById(id);
    }

    private Category toDomain(CategoryEntity entity) {
        Category category = new Category(entity.getId(), new Name(entity.getName()));
        if (Boolean.TRUE.equals(entity.getActive())) {
            category.activate();
        } else {
            category.deactivate();
        }
        return category;
    }

    private CategoryEntity toEntity(Category category) {
        return new CategoryEntity(category.getId(), category.getName().value(), category.isActive());
    }
}
