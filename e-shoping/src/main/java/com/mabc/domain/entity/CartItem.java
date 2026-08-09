package com.mabc.domain.entity;

import com.mabc.domain.valueobject.Quantity;

import java.util.Objects;

public class CartItem {

    private final Long id;
    private final Product product;
    private Quantity quantity;
    private double subTotal;

    public CartItem(Long id, Product product, Quantity quantity) {
        this.id = Objects.requireNonNull(id, "El ID del ítem no puede ser nulo.");
        this.product = Objects.requireNonNull(product, "El producto del ítem no puede ser nulo.");
        this.quantity = Objects.requireNonNull(quantity, "La cantidad del ítem no puede ser nula.");
        this.subTotal = product.getPriceSale().value() * quantity.value();
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void changeQuantity(Quantity newQuantity) {
        this.quantity = Objects.requireNonNull(newQuantity, "La nueva cantidad no puede ser nula.");
        this.subTotal = product.getPriceSale().value() * quantity.value();
    }

    public void calculateSubTotal() {
        this.subTotal = product.getPriceSale().value() * quantity.value();
    }
}
