package com.mabc.domain.entity;

import com.mabc.domain.valueobject.Description;
import com.mabc.domain.valueobject.Name;
import com.mabc.domain.valueobject.Price;
import com.mabc.domain.valueobject.Quantity;
import com.mabc.domain.valueobject.Stock;
import com.mabc.domain.valueobject.Weight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartTest {

    private Cart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        Mark mark = new Mark(1L, new Name("Lenovo"));
        product = new Product(1L, mark, List.of(),
                new Name("Notebook Lenovo"), new Description("Notebook Lenovo IdeaPad 310"),
                new Stock(12), new Weight(1500), new Price(650000), new Price(800000));
        cart = new Cart(1L);
    }

    @Test
    @DisplayName("Carrito nuevo: inicia vacio con subtotal en cero")
    void newCartStartsWithZeroSubTotal() {
        assertTrue(cart.getItems().isEmpty());
        assertEquals(0.0, cart.getSubTotal());
        assertNotNull(cart.getCreationDate());
    }

    @Test
    @DisplayName("addItem: agrega un item y recalcula el subtotal")
    void addItemAddsAndRecalculatesSubTotal() {
        cart.addItem(product, new Quantity(2));

        assertEquals(1, cart.getItems().size());
        assertEquals(1600000, cart.getSubTotal());
    }

    @Test
    @DisplayName("addItem: lanza excepcion si no hay stock suficiente")
    void addItemRejectsInsufficientStock() {
        assertThrows(IllegalStateException.class, () -> cart.addItem(product, new Quantity(99)));
        assertTrue(cart.getItems().isEmpty());
    }
}
