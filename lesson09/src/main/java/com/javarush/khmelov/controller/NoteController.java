package com.javarush.khmelov.controller;

import com.javarush.khmelov.model.note.Note;
import com.javarush.khmelov.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public Collection<Note.Out> getAll() {
        return noteService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note.Out create(@RequestBody @Valid Note.In inputDto) {
        return noteService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Note.Out update(@RequestBody @Valid Note.In inputDto) {
        try {
            return noteService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public Note.Out read(@PathVariable long id) {
        return noteService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = noteService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
