package com.mabc.domain.valueobject;

import com.mabc.domain.exception.InvalidWeightException;

public record Weight(double value) {

    public Weight {
        if (value <= 0) {
            throw new InvalidWeightException("El peso debe ser mayor a cero.");
        }
    }
}
