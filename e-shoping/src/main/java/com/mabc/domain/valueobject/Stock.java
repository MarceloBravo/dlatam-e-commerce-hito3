package com.mabc.domain.valueobject;

import com.mabc.domain.exception.InvalidStockException;

public record Stock(int value) {

    public Stock {
        if (value < 0) {
            throw new InvalidStockException("El stock no puede ser negativo.");
        }
    }
}
