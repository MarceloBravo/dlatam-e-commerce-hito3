package com.mabc.infrastructure.persistence.jpa;

import com.mabc.domain.entity.Mark;
import com.mabc.domain.repository.MarkRepository;
import com.mabc.domain.valueobject.Name;
import com.mabc.infrastructure.persistence.entity.MarkEntity;
import com.mabc.infrastructure.persistence.spring.MarkJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaMarkRepository implements MarkRepository {

    private final MarkJpaRepository markJpaRepository;

    public JpaMarkRepository(MarkJpaRepository markJpaRepository) {
        this.markJpaRepository = markJpaRepository;
    }

    @Override
    public Optional<Mark> findById(Long id) {
        return markJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Mark> findAll() {
        return markJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Mark save(Mark mark) {
        MarkEntity saved = markJpaRepository.save(toEntity(mark));
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        markJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return markJpaRepository.existsById(id);
    }

    private Mark toDomain(MarkEntity entity) {
        Mark mark = new Mark(entity.getId(), new Name(entity.getName()));
        if (Boolean.TRUE.equals(entity.getActive())) {
            mark.activate();
        } else {
            mark.deactivate();
        }
        return mark;
    }

    private MarkEntity toEntity(Mark mark) {
        return new MarkEntity(mark.getId(), mark.getName().value(), mark.isActive());
    }
}
