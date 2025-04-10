package com.javarush.khmelov.service;

import com.javarush.khmelov.model.tag.Tag;
import com.javarush.khmelov.mapper.TagDto;
import com.javarush.khmelov.repository.impl.TagRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    public final TagRepoImpl repoImpl;
    public final TagDto mapper;

    public List<Tag.Out> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public Tag.Out get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public Tag.Out create(Tag.In input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public Tag.Out update(Tag.In input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
