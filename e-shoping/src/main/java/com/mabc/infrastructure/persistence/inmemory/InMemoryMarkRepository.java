package com.mabc.infrastructure.persistence.inmemory;

import com.mabc.domain.entity.Mark;
import com.mabc.domain.repository.MarkRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryMarkRepository implements MarkRepository {

    private final Map<Long, Mark> store = new LinkedHashMap<>();

    @Override
    public Optional<Mark> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Mark> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Mark save(Mark mark) {
        store.put(mark.getId(), mark);
        return mark;
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}