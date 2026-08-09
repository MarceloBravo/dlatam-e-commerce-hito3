package com.mabc.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

public class CartDTO {
    private Long id;
    private List<CartItemDTO> products = new ArrayList<>();
    private LocalDateTime creationDate = LocalDateTime.now(ZoneId.of("America/Santiago"));
    private Double subTotal = 0.0;

    public CartDTO(){}
    
    public CartDTO(Long id, List<CartItemDTO> products){
        this.id = id;
        this.products = products;
        this.creationDate = creationDate;
        this.subTotal = subTotal;
    }

    // Getters && Setters
    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }
    
    public void setProducts(List<CartItemDTO> products){
        this.products = products;
    }

    public List<CartItemDTO> getProducts(){
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