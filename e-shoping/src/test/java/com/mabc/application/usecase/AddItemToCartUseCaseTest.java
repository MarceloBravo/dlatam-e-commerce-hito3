package com.mabc.application.usecase;

import com.mabc.domain.entity.Cart;
import com.mabc.domain.entity.Mark;
import com.mabc.domain.entity.Product;
import com.mabc.domain.valueobject.Description;
import com.mabc.domain.valueobject.Name;
import com.mabc.domain.valueobject.Price;
import com.mabc.domain.valueobject.Stock;
import com.mabc.domain.valueobject.Weight;
import com.mabc.infrastructure.persistence.inmemory.InMemoryCartRepository;
import com.mabc.infrastructure.persistence.inmemory.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddItemToCartUseCaseTest {

    private InMemoryCartRepository cartRepository;
    private InMemoryProductRepository productRepository;
    private AddItemToCartUseCase useCase;
    private Product product;
    private Cart cart;

    @BeforeEach
    void setUp() {
        cartRepository = new InMemoryCartRepository();
        productRepository = new InMemoryProductRepository();
        useCase = new AddItemToCartUseCase(cartRepository, productRepository);

        Mark mark = new Mark(1L, new Name("Lenovo"));
        product = new Product(1L, mark, List.of(),
                new Name("Notebook Lenovo"), new Description("Notebook Lenovo IdeaPad 310"),
                new Stock(12), new Weight(1500), new Price(650000), new Price(800000));
        productRepository.save(product);

        cart = new Cart(1L);
        cartRepository.save(cart);
    }

    @Test
    @DisplayName("Agrega un producto al carrito y recalcula el subtotal")
    void addsProductToCart() {
        Cart result = useCase.execute(1L, 1L, 2);

        assertEquals(1, result.getItems().size());
        assertEquals(1600000, result.getSubTotal());
    }

    @Test
    @DisplayName("Lanza excepcion si el carrito no existe")
    void rejectsWhenCartNotExists() {
        assertThrows(IllegalStateException.class, () -> useCase.execute(999L, 1L, 1));
    }

    @Test
    @DisplayName("Lanza excepcion si el producto no existe")
    void rejectsWhenProductNotExists() {
        assertThrows(IllegalStateException.class, () -> useCase.execute(1L, 999L, 1));
    }

    @Test
    @DisplayName("Lanza excepcion si no hay stock suficiente y no guarda cambios")
    void rejectsWhenStockInsufficient() {
        assertThrows(IllegalStateException.class, () -> useCase.execute(1L, 1L, 50));
        assertEquals(0, cartRepository.findById(1L).orElseThrow().getItems().size());
    }
}
