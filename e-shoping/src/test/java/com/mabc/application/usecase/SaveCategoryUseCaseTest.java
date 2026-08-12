package com.mabc.application.usecase;

import com.mabc.domain.entity.Category;
import com.mabc.domain.exception.InvalidNameException;
import com.mabc.infrastructure.persistence.inmemory.InMemoryCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveCategoryUseCaseTest {

    @Test
    @DisplayName("Crea una categoria nueva activa")
    void createsCategory() {
        InMemoryCategoryRepository repository = new InMemoryCategoryRepository();
        SaveCategoryUseCase useCase = new SaveCategoryUseCase(repository);

        Category category = useCase.execute(null, "Computacion", true);

        assertEquals(1L, category.getId());
        assertEquals("Computacion", category.getName().value());
        assertTrue(category.isActive());
    }

    @Test
    @DisplayName("Actualiza una categoria existente")
    void updatesCategory() {
        InMemoryCategoryRepository repository = new InMemoryCategoryRepository();
        SaveCategoryUseCase useCase = new SaveCategoryUseCase(repository);
        Category created = useCase.execute(null, "Computacion", true);

        Category updated = useCase.execute(created.getId(), "Gaming", false);

        assertEquals(created.getId(), updated.getId());
        assertEquals("Gaming", updated.getName().value());
        assertFalse(updated.isActive());
    }

    @Test
    @DisplayName("Rechaza nombre nulo o vacio")
    void rejectsBlankName() {
        InMemoryCategoryRepository repository = new InMemoryCategoryRepository();
        SaveCategoryUseCase useCase = new SaveCategoryUseCase(repository);

        assertThrows(InvalidNameException.class, () -> useCase.execute(null, "   ", true));
    }

    @Test
    @DisplayName("Lanza excepcion si se actualiza una categoria inexistente")
    void rejectsUpdatingMissingCategory() {
        InMemoryCategoryRepository repository = new InMemoryCategoryRepository();
        SaveCategoryUseCase useCase = new SaveCategoryUseCase(repository);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(99L, "Gaming", true));
    }

    @Test
    @DisplayName("Activa y desactiva la categoria segun el estado recibido")
    void togglesActiveState() {
        InMemoryCategoryRepository repository = new InMemoryCategoryRepository();
        SaveCategoryUseCase useCase = new SaveCategoryUseCase(repository);
        Category created = useCase.execute(null, "Computacion", false);

        assertFalse(created.isActive());

        Category reactivated = useCase.execute(created.getId(), "Computacion", true);

        assertTrue(reactivated.isActive());
    }
}
