package com.javarush.khmelov.service;

import com.javarush.khmelov.model.editor.Editor;
import com.javarush.khmelov.mapper.EditorDto;
import com.javarush.khmelov.repository.impl.EditorRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EditorService {

    public final EditorRepoImpl repoImpl;
    public final EditorDto mapper;

    public List<Editor.Out> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public Editor.Out get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public Editor.Out create(Editor.In input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public Editor.Out update(Editor.In input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
