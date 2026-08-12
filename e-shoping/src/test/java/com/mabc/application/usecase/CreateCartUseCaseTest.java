package com.mabc.application.usecase;

import com.mabc.domain.entity.Cart;
import com.mabc.infrastructure.persistence.inmemory.InMemoryCartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateCartUseCaseTest {

    @Test
    @DisplayName("Crea un carrito con id 1 si no existen carritos previos")
    void createsCartWithIdOneWhenNoPrevious() {
        InMemoryCartRepository repository = new InMemoryCartRepository();
        CreateCartUseCase useCase = new CreateCartUseCase(repository);

        Cart cart = useCase.execute();

        assertNotNull(cart);
        assertEquals(1L, cart.getId());
        assertEquals(0.0, cart.getSubTotal());
    }

    @Test
    @DisplayName("Crea un carrito con el id siguiente al ultimo carrito")
    void createsCartWithNextId() {
        InMemoryCartRepository repository = new InMemoryCartRepository();
        repository.save(new Cart(1000L));
        CreateCartUseCase useCase = new CreateCartUseCase(repository);

        Cart cart = useCase.execute();

        assertEquals(1001L, cart.getId());
    }
}
