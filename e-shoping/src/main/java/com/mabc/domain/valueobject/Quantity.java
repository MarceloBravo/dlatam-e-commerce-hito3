package com.mabc.domain.valueobject;

import com.mabc.domain.exception.InvalidQuantityException;

public record Quantity(int value) {

    public Quantity {
        if (value <= 0) {
            throw new InvalidQuantityException("La cantidad debe ser mayor a cero.");
        }
    }
}
