package com.javarush.khmelov.mapper;

import com.javarush.khmelov.model.note.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteDto {
    Note.Out out(Note entity);

    Note in(Note.In inputDto);
}
