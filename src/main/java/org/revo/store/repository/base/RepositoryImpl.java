package org.revo.store.repository.base;

import org.revo.store.domain.EntityId;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class RepositoryImpl<T extends EntityId, K> extends EntityFileSystem<T> implements Repository<T, K> {

    @Override
    public List<T> findAll() {
        return getData();
    }

    @Override
    public T save(T t) {
        Integer nextId = getData().size() + 1;
        t.setId(nextId.longValue());
        getData().add(t);
        return t;
    }

    @Override
    public T save(K id, T t) {
        List<T> data = getData().stream().map(it -> {
            if (it.getId().equals(id))
                return t;
            else
                return it;
        }).collect(Collectors.toList());
        setData(data);
        return t;
    }

    @Override
    public List<T> search(String searchCriteria, Predicate<T> predicate) {
        return getData().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Optional<T> findOne(Long id) {
        return getData().stream().filter(it -> it.getId().equals(id)).findFirst();
    }
}
