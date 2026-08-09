package com.mabc.application.usecase;

import com.mabc.domain.entity.Mark;
import com.mabc.domain.repository.MarkRepository;
import com.mabc.domain.valueobject.Name;

public class SaveMarkUseCase {

    private final MarkRepository markRepository;

    public SaveMarkUseCase(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public Mark execute(Long id, String name, boolean active) {
        Name markName = new Name(name);

        Mark mark;
        if (id == null) {
            mark = new Mark(nextId(), markName);
        } else {
            mark = markRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("La marca no existe."));
            mark.rename(markName);
        }

        if (active) {
            mark.activate();
        } else {
            mark.deactivate();
        }

        return markRepository.save(mark);
    }

    private Long nextId() {
        return markRepository.findAll().stream()
                .mapToLong(Mark::getId)
                .max()
                .orElse(0L) + 1;
    }
}
