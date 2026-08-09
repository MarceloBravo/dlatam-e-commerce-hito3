package com.mabc.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class CartItemDTO {
    
    private Long id;
    private ProductDTO product;
    private Integer cant;
    private Double subTotal;

    public CartItemDTO(){}

    public CartItemDTO(Long id, ProductDTO product, Integer cant, Double subTotal){
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

    public void setProduct(ProductDTO product){
        this.product = product;
    }

    public ProductDTO getProduct(){
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