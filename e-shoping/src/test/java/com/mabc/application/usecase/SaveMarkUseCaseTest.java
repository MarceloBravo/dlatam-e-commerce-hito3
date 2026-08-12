package com.mabc.application.usecase;

import com.mabc.domain.entity.Mark;
import com.mabc.domain.exception.InvalidNameException;
import com.mabc.infrastructure.persistence.inmemory.InMemoryMarkRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveMarkUseCaseTest {

    @Test
    @DisplayName("Crea una marca nueva activa")
    void createsMark() {
        InMemoryMarkRepository repository = new InMemoryMarkRepository();
        SaveMarkUseCase useCase = new SaveMarkUseCase(repository);

        Mark mark = useCase.execute(null, "Lenovo", true);

        assertEquals(1L, mark.getId());
        assertEquals("Lenovo", mark.getName().value());
        assertTrue(mark.isActive());
    }

    @Test
    @DisplayName("Actualiza una marca existente")
    void updatesMark() {
        InMemoryMarkRepository repository = new InMemoryMarkRepository();
        SaveMarkUseCase useCase = new SaveMarkUseCase(repository);
        Mark created = useCase.execute(null, "Lenovo", true);

        Mark updated = useCase.execute(created.getId(), "Asus", false);

        assertEquals(created.getId(), updated.getId());
        assertEquals("Asus", updated.getName().value());
        assertFalse(updated.isActive());
    }

    @Test
    @DisplayName("Rechaza nombre nulo o vacio")
    void rejectsBlankName() {
        InMemoryMarkRepository repository = new InMemoryMarkRepository();
        SaveMarkUseCase useCase = new SaveMarkUseCase(repository);

        assertThrows(InvalidNameException.class, () -> useCase.execute(null, "", true));
    }
}
