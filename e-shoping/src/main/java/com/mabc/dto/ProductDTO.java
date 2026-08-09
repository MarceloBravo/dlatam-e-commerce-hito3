package com.mabc.dto;

import java.util.List;
import java.util.ArrayList;

public class ProductDTO {
    private Long id;
    private MarkDTO mark;
    private List<CategoryDTO> categories = new ArrayList<>();
    private String name;
    private String description;
    private int stock;
    private double weight;
    private double priceCost;
    private double priceSale;

    public ProductDTO() {
    }

    public ProductDTO(Long id, MarkDTO mark, List<CategoryDTO> categories, String name, String description, int stock, double weight, double priceCost, double priceSale) {
        this.id = id;
        this.mark = mark;
        this.categories = categories;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.weight = weight;
        this.priceCost = priceCost;
        this.priceSale = priceSale;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarkDTO getMark() {
        return mark;
    }

    public void setMark(MarkDTO mark) {
        this.mark = mark;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPriceCost() {
        return priceCost;
    }

    public void setPriceCost(double priceCost) {
        this.priceCost = priceCost;
    }

    public double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(double priceSale) {
        this.priceSale = priceSale;
    }
    
}