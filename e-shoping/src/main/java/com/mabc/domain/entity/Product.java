package com.mabc.domain.entity;

import com.mabc.domain.valueobject.Description;
import com.mabc.domain.valueobject.Name;
import com.mabc.domain.valueobject.Price;
import com.mabc.domain.valueobject.Quantity;
import com.mabc.domain.valueobject.Stock;
import com.mabc.domain.valueobject.Weight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Product {

    private final Long id;
    private final Mark mark;
    private final List<Category> categories;
    private Name name;
    private Description description;
    private Stock stock;
    private Weight weight;
    private Price priceCost;
    private Price priceSale;

    public Product(
        Long id, 
        Mark mark, 
        List<Category> categories, 
        Name name, 
        Description description,
        Stock stock, 
        Weight weight, 
        Price priceCost, 
        Price priceSale
    ) {
        this.id = Objects.requireNonNull(id, "El ID del producto no puede ser nulo.");
        this.mark = Objects.requireNonNull(mark, "La marca del producto no puede ser nula.");
        this.categories = categories == null ? new ArrayList<>() : new ArrayList<>(categories);
        this.name = Objects.requireNonNull(name, "El nombre del producto no puede ser nulo.");
        this.description = Objects.requireNonNull(description, "La descripción del producto no puede ser nula.");
        this.stock = Objects.requireNonNull(stock, "El stock del producto no puede ser nulo.");
        this.weight = Objects.requireNonNull(weight, "El peso del producto no puede ser nulo.");
        this.priceCost = Objects.requireNonNull(priceCost, "El precio de costo no puede ser nulo.");
        this.priceSale = Objects.requireNonNull(priceSale, "El precio de venta no puede ser nulo.");
    }

    public Long getId() {
        return id;
    }

    public Mark getMark() {
        return mark;
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Stock getStock() {
        return stock;
    }

    public Weight getWeight() {
        return weight;
    }

    public Price getPriceCost() {
        return priceCost;
    }

    public Price getPriceSale() {
        return priceSale;
    }

    public void rename(Name newName) {
        this.name = Objects.requireNonNull(newName, "El nuevo nombre del producto no puede ser nulo.");
    }

    public void updateDescription(Description newDescription) {
        this.description = Objects.requireNonNull(newDescription, "La nueva descripción no puede ser nula.");
    }

    public void restock(Stock newStock) {
        this.stock = Objects.requireNonNull(newStock, "El nuevo stock no puede ser nulo.");
    }

    public void updatePrices(Price newCost, Price newSale) {
        this.priceCost = Objects.requireNonNull(newCost, "El nuevo precio de costo no puede ser nulo.");
        this.priceSale = Objects.requireNonNull(newSale, "El nuevo precio de venta no puede ser nulo.");
    }

    public boolean hasStock(Quantity quantity) {
        return this.stock.value() >= quantity.value();
    }

    public void reduceStock(Quantity quantity) {
        if (!hasStock(quantity)) {
            throw new IllegalStateException("Stock insuficiente para el producto " + name.value());
        }
        this.stock = new Stock(this.stock.value() - quantity.value());
    }
}
