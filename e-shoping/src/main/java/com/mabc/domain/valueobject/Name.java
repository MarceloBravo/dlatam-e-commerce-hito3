package com.mabc.domain.valueobject;

import com.mabc.domain.exception.InvalidNameException;

public record Name(String value) {

    public Name {
        if (value == null || value.isBlank()) {
            throw new InvalidNameException("El nombre no puede ser nulo o estar vacío.");
        }
        value = value.trim();
    }
}
