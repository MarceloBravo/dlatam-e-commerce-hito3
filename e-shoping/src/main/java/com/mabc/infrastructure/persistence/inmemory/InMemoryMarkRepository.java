package com.mabc.infrastructure.persistence.inmemory;

import com.mabc.domain.entity.Mark;
import com.mabc.domain.repository.MarkRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación en memoria de {@link MarkRepository}.
 *
 * <p>Almacena las marcas en un {@link LinkedHashMap} que conserva el orden
 * de inserción, sin persistencia entre ejecuciones de la aplicación.
 */
public class InMemoryMarkRepository implements MarkRepository {

    private final Map<Long, Mark> store = new LinkedHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Mark> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mark> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mark save(Mark mark) {
        store.put(mark.getId(), mark);
        return mark;
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