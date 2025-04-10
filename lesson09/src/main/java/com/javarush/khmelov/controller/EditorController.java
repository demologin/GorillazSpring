package com.javarush.khmelov.controller;

import com.javarush.khmelov.model.editor.Editor;
import com.javarush.khmelov.service.EditorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1.0/editors")
public class EditorController {

    private final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @GetMapping
    public Collection<Editor.Out> getAll() {
        return editorService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Editor.Out create(@RequestBody @Valid Editor.In inputDto) {
        return editorService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Editor.Out update(@RequestBody @Valid Editor.In inputDto) {
        try {
            return editorService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public Editor.Out read(@PathVariable long id) {
        return editorService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = editorService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
