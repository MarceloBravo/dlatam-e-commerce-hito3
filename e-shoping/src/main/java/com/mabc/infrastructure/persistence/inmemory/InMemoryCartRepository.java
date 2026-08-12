package com.mabc.infrastructure.persistence.inmemory;

import com.mabc.domain.entity.Cart;
import com.mabc.domain.repository.CartRepository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación en memoria de {@link CartRepository}.
 *
 * <p>Almacena los carritos en un {@link LinkedHashMap} que conserva el orden
 * de inserción, sin persistencia entre ejecuciones de la aplicación.
 */
public class InMemoryCartRepository implements CartRepository {

    private final Map<Long, Cart> store = new LinkedHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cart> findLast() {
        return store.values().stream().max(Comparator.comparing(Cart::getId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart save(Cart cart) {
        store.put(cart.getId(), cart);
        return cart;
    }
}