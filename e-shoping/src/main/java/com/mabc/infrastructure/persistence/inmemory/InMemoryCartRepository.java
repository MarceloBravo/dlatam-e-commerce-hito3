package com.mabc.infrastructure.persistence.inmemory;

import com.mabc.domain.entity.Cart;
import com.mabc.domain.repository.CartRepository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCartRepository implements CartRepository {

    private final Map<Long, Cart> store = new LinkedHashMap<>();

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Cart> findLast() {
        return store.values().stream().max(Comparator.comparing(Cart::getId));
    }

    @Override
    public Cart save(Cart cart) {
        store.put(cart.getId(), cart);
        return cart;
    }
}