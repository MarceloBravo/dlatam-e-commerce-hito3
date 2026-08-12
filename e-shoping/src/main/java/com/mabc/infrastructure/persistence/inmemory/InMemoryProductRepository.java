package com.mabc.infrastructure.persistence.inmemory;

import com.mabc.domain.entity.Product;
import com.mabc.domain.repository.ProductRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación en memoria de {@link ProductRepository}.
 *
 * <p>Almacena los productos en un {@link LinkedHashMap} que conserva el orden
 * de inserción, sin persistencia entre ejecuciones de la aplicación.
 */
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> store = new LinkedHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product save(Product product) {
        store.put(product.getId(), product);
        return product;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}