package com.mabc.infrastructure.persistence.jpa;

import com.mabc.domain.entity.Category;
import com.mabc.domain.entity.Mark;
import com.mabc.domain.entity.Product;
import com.mabc.domain.valueobject.Description;
import com.mabc.domain.valueobject.Name;
import com.mabc.domain.valueobject.Price;
import com.mabc.domain.valueobject.Stock;
import com.mabc.domain.valueobject.Weight;
import com.mabc.infrastructure.persistence.entity.CategoryEntity;
import com.mabc.infrastructure.persistence.entity.MarkEntity;
import com.mabc.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public final class ProductEntityMapper {

    private ProductEntityMapper() {
    }

    public static Product toDomain(ProductEntity entity) {
        MarkEntity markEntity = entity.getMark();
        Mark mark = new Mark(markEntity.getId(), new Name(markEntity.getName()));
        if (Boolean.TRUE.equals(markEntity.getActive())) {
            mark.activate();
        } else {
            mark.deactivate();
        }

        List<Category> categories = entity.getCategories() == null ? List.of()
                : entity.getCategories().stream()
                        .map(ProductEntityMapper::toDomainCategory)
                        .toList();

        return new Product(
                entity.getId(),
                mark,
                categories,
                new Name(entity.getName()),
                new Description(entity.getDescription()),
                new Stock(entity.getStock()),
                new Weight(entity.getWeight()),
                new Price(entity.getPriceCost()),
                new Price(entity.getPriceSale()));
    }

    public static ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName().value());
        entity.setDescription(product.getDescription().value());
        entity.setStock(product.getStock().value());
        entity.setWeight(product.getWeight().value());
        entity.setPriceCost(product.getPriceCost().value());
        entity.setPriceSale(product.getPriceSale().value());

        entity.setMark(new MarkEntity(
                product.getMark().getId(),
                product.getMark().getName().value(),
                product.getMark().isActive()));

        List<CategoryEntity> categories = product.getCategories().stream()
                .map(category -> new CategoryEntity(
                        category.getId(),
                        category.getName().value(),
                        category.isActive()))
                .toList();
        entity.setCategories(categories);
        return entity;
    }

    private static Category toDomainCategory(CategoryEntity entity) {
        Category category = new Category(entity.getId(), new Name(entity.getName()));
        if (Boolean.TRUE.equals(entity.getActive())) {
            category.activate();
        } else {
            category.deactivate();
        }
        return category;
    }
}
