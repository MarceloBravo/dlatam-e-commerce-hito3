package com.mabc.domain.entity;

import com.mabc.domain.valueobject.Quantity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final Long id;
    private final List<CartItem> items;
    private final LocalDateTime creationDate;
    private double subTotal;

    public Cart(Long id) {
        this.id = Objects.requireNonNull(id, "El ID del carrito no puede ser nulo.");
        this.items = new ArrayList<>();
        this.creationDate = LocalDateTime.now(ZoneId.of("America/Santiago"));
        this.subTotal = 0.0;
    }

    public Long getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public CartItem addItem(Product product, Quantity quantity) {
        Objects.requireNonNull(product, "El producto no puede ser nulo.");
        Objects.requireNonNull(quantity, "La cantidad no puede ser nula.");

        if (!product.hasStock(quantity)) {
            throw new IllegalStateException("Stock insuficiente para el producto " + product.getName().value());
        }

        CartItem item = new CartItem((long) (items.size() + 1), product, quantity);
        items.add(item);
        calculateSubTotal();
        return item;
    }

    public void calculateSubTotal() {
        this.subTotal = items.stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
    }
}
