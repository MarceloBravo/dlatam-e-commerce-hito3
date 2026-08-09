package com.mabc.domain.entity;

import com.mabc.domain.valueobject.Name;

import java.util.Objects;

public class Category {

    private final Long id;
    private Name name;
    private boolean active;

    public Category(Long id, Name name) {
        this.id = Objects.requireNonNull(id, "El ID de la categoría no puede ser nulo.");
        this.name = Objects.requireNonNull(name, "El nombre de la categoría no puede ser nulo.");
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void rename(Name newName) {
        this.name = Objects.requireNonNull(newName, "El nuevo nombre de la categoría no puede ser nulo.");
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
