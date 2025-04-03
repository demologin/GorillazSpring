package com.javarush.khmelov.mapper;

import com.javarush.khmelov.model.editor.Editor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditorDto {
    Editor.Out out(Editor entity);

    Editor in(Editor.In inputDto);
}
