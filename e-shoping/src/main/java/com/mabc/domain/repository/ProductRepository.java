package com.mabc.domain.repository;

import com.mabc.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Product save(Product product);

    void deleteById(Long id);

    boolean existsById(Long id);
}
