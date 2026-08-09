package com.mabc.entities;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

import jakarta.validation.constraints.DecimalMin;


public class Cart {

    private Long id;

    private List<CartItem> products = new ArrayList<>();

    private LocalDateTime creationDate;

    private Double subTotal;

    public Cart(){}
    
    public Cart(Long id, List<CartItem> products){
        this.id = id;
        this.products = products;
        this.creationDate = LocalDateTime.now(ZoneId.of("America/Santiago"));
        this.subTotal = 0.0;
    }

    // Getters && Setters
    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }
    
    public void setProducts(List<CartItem> products){
        this.products = products;
    }

    public List<CartItem> getProducts(){
        return this.products;
    }
    
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }
    
    public void setSubTotal(Double subTotal){
        this.subTotal = subTotal;
    }

    public Double getSubTotal(){
        return this.subTotal;
    }
}
