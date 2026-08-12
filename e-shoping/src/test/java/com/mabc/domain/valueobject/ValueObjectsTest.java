package com.mabc.domain.valueobject;

import com.mabc.domain.exception.InvalidDescriptionException;
import com.mabc.domain.exception.InvalidNameException;
import com.mabc.domain.exception.InvalidPriceException;
import com.mabc.domain.exception.InvalidQuantityException;
import com.mabc.domain.exception.InvalidStockException;
import com.mabc.domain.exception.InvalidWeightException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValueObjectsTest {

    @Test
    @DisplayName("Name: acepta un valor valido y lo normaliza")
    void nameAcceptsValidValue() {
        assertEquals("Lenovo", new Name(" Lenovo ").value());
    }

    @Test
    @DisplayName("Name: rechaza valores nulos o vacios")
    void nameRejectsNullOrBlank() {
        assertThrows(InvalidNameException.class, () -> new Name(null));
        assertThrows(InvalidNameException.class, () -> new Name("   "));
    }

    @Test
    @DisplayName("Description: acepta un valor valido")
    void descriptionAcceptsValidValue() {
        assertEquals("Notebook", new Description(" Notebook ").value());
    }

    @Test
    @DisplayName("Description: rechaza valores nulos o vacios")
    void descriptionRejectsNullOrBlank() {
        assertThrows(InvalidDescriptionException.class, () -> new Description(null));
        assertThrows(InvalidDescriptionException.class, () -> new Description(""));
    }

    @Test
    @DisplayName("Price: rechaza precios negativos")
    void priceRejectsNegative() {
        assertThrows(InvalidPriceException.class, () -> new Price(-1));
        assertEquals(0, new Price(0).value());
    }

    @Test
    @DisplayName("Stock: rechaza stock negativo")
    void stockRejectsNegative() {
        assertThrows(InvalidStockException.class, () -> new Stock(-1));
        assertEquals(0, new Stock(0).value());
    }

    @Test
    @DisplayName("Weight: rechaza pesos menores o iguales a cero")
    void weightRejectsZeroOrLess() {
        assertThrows(InvalidWeightException.class, () -> new Weight(0));
        assertThrows(InvalidWeightException.class, () -> new Weight(-5));
        assertEquals(1.5, new Weight(1.5).value());
    }

    @Test
    @DisplayName("Quantity: rechaza cantidades menores o iguales a cero")
    void quantityRejectsZeroOrLess() {
        assertThrows(InvalidQuantityException.class, () -> new Quantity(0));
        assertThrows(InvalidQuantityException.class, () -> new Quantity(-2));
        assertEquals(3, new Quantity(3).value());
    }
}
