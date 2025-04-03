package com.javarush.khmelov.mapper;

import com.javarush.khmelov.model.tag.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagDto {
    Tag.Out out(Tag entity);

    Tag in(Tag.In inputDto);
}
