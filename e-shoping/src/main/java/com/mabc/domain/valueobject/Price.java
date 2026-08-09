package com.mabc.domain.valueobject;

import com.mabc.domain.exception.InvalidPriceException;

public record Price(double value) {

    public Price {
        if (value < 0) {
            throw new InvalidPriceException("El precio no puede ser negativo.");
        }
    }
}
