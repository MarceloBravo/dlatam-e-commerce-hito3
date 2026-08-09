package com.mabc.entities;


import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;


public class CartItem{

    private Long id;

    private Product product;

    private Integer cant;

    private Double subTotal;

    public CartItem(){}

    public CartItem(Long id, Product product, Integer cant, Double subTotal){
        this.id = id;
        this.product = product;
        this.cant = cant;
        this.subTotal = subTotal;
    }

    // Getters && Setters
    public void setId(Long id){
        this.id = id;
    }
    
    public Long getId(){
        return this.id;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public Product getProduct(){
        return this.product;
    }

    public void setCant(Integer cant){
        this.cant = cant;
    }

    public Integer getCant(){
        return this.cant;
    }

    public void setSubTotal(Double subTotal){
        this.subTotal = subTotal;
    }

    public Double getSubTotal(){
        return this.subTotal;
    }
}