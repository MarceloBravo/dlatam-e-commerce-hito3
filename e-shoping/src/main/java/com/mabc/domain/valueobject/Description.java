package com.mabc.domain.valueobject;

import com.mabc.domain.exception.InvalidDescriptionException;

public record Description(String value) {

    public Description {
        if (value == null || value.isBlank()) {
            throw new InvalidDescriptionException("La descripción no puede ser nula o estar vacía.");
        }
        value = value.trim();
    }
}
