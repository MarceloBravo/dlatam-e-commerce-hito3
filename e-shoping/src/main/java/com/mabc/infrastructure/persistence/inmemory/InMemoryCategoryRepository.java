package com.mabc.infrastructure.persistence.inmemory;

import com.mabc.domain.entity.Category;
import com.mabc.domain.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementación en memoria de {@link CategoryRepository}.
 *
 * <p>Almacena las categorías en un {@link LinkedHashMap} que conserva el
 * orden de inserción, sin persistencia entre ejecuciones de la aplicación.
 */
public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<Long, Category> store = new LinkedHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAllByIds(List<Long> ids) {
        return ids.stream().map(store::get).filter(Objects::nonNull).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category save(Category category) {
        store.put(category.getId(), category);
        return category;
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