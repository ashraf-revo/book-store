package org.revo.store.repository.base;

import org.revo.store.domain.EntityId;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Repository<T extends EntityId, K> {
    List<T> findAll();

    T save(T t);

    T save(K id, T t);

    List<T> search(String searchCriteria, Predicate<T> predicate);

    void flush();

    Optional<T> findOne(Long id);
}
