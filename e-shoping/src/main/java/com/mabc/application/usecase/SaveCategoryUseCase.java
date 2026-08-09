package com.mabc.application.usecase;

import com.mabc.domain.entity.Category;
import com.mabc.domain.repository.CategoryRepository;
import com.mabc.domain.valueobject.Name;

import java.util.List;

public class SaveCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public SaveCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category execute(Long id, String name, boolean active) {
        Name categoryName = new Name(name);

        Category category;
        if (id == null) {
            category = new Category(nextId(), categoryName);
        } else {
            category = categoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("La categoría no existe."));
            category.rename(categoryName);
        }

        if (active) {
            category.activate();
        } else {
            category.deactivate();
        }

        return categoryRepository.save(category);
    }

    private Long nextId() {
        return categoryRepository.findAll().stream()
                .mapToLong(Category::getId)
                .max()
                .orElse(0L) + 1;
    }
}
