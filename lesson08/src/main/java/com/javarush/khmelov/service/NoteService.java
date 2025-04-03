package com.javarush.khmelov.service;

import com.javarush.khmelov.model.note.Note;
import com.javarush.khmelov.mapper.NoteDto;
import com.javarush.khmelov.repository.impl.NoteRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    public final NoteRepoImpl repoImpl;
    public final NoteDto mapper;

    public List<Note.Out> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public Note.Out get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public Note.Out create(Note.In input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public Note.Out update(Note.In input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
