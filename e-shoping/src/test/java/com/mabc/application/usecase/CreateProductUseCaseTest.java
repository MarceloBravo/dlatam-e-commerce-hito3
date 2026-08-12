package com.mabc.application.usecase;

import com.mabc.domain.entity.Category;
import com.mabc.domain.entity.Mark;
import com.mabc.domain.entity.Product;
import com.mabc.domain.valueobject.Name;
import com.mabc.infrastructure.persistence.inmemory.InMemoryCategoryRepository;
import com.mabc.infrastructure.persistence.inmemory.InMemoryMarkRepository;
import com.mabc.infrastructure.persistence.inmemory.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateProductUseCaseTest {

    private InMemoryProductRepository productRepository;
    private InMemoryCategoryRepository categoryRepository;
    private InMemoryMarkRepository markRepository;
    private CreateProductUseCase useCase;
    private Mark mark;
    private Category category;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        categoryRepository = new InMemoryCategoryRepository();
        markRepository = new InMemoryMarkRepository();
        useCase = new CreateProductUseCase(productRepository, categoryRepository, markRepository);

        mark = markRepository.save(new Mark(1L, new Name("Lenovo")));
        category = categoryRepository.save(new Category(1L, new Name("Computacion")));
    }

    @Test
    @DisplayName("Crea un producto nuevo con sus value objects")
    void createsProduct() {
        Product product = useCase.execute(null, 1L, List.of(1L),
                "Notebook Lenovo", "Notebook Lenovo IdeaPad 310", 12, 1500, 650000, 800000);

        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Notebook Lenovo", product.getName().value());
        assertEquals(12, product.getStock().value());
        assertEquals(800000, product.getPriceSale().value());
    }

    @Test
    @DisplayName("Actualiza un producto existente manteniendo el id")
    void updatesProductKeepingId() {
        Product existing = useCase.execute(null, 1L, List.of(1L),
                "Old", "Desc", 10, 1500, 650000, 800000);

        Product updated = useCase.execute(existing.getId(), 1L, List.of(1L),
                "New", "Desc2", 5, 1500, 700000, 900000);

        assertEquals(existing.getId(), updated.getId());
        assertEquals("New", updated.getName().value());
        assertEquals(5, updated.getStock().value());
        assertEquals(900000, updated.getPriceSale().value());
    }

    @Test
    @DisplayName("Lanza excepcion si la marca no existe")
    void rejectsWhenMarkNotExists() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(
                null, 999L, List.of(1L), "Name", "Desc", 1, 1500, 1, 2));
    }

    @Test
    @DisplayName("Lanza excepcion si alguna categoria no existe")
    void rejectsWhenCategoryNotExists() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(
                null, 1L, List.of(999L), "Name", "Desc", 1, 1500, 1, 2));
    }
}
