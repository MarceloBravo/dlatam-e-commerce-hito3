package com.mabc.domain.repository;

import com.mabc.domain.entity.Cart;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findById(Long id);

    Optional<Cart> findLast();

    Cart save(Cart cart);
}
