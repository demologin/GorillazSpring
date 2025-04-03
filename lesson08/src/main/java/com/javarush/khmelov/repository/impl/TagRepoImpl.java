package com.javarush.khmelov.repository.impl;

import com.javarush.khmelov.model.tag.Tag;
import com.javarush.khmelov.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class TagRepoImpl implements Repo<Tag> {

    Map<Long, Tag> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Tag> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Tag> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Tag> create(Tag input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Tag> update(Tag input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}
