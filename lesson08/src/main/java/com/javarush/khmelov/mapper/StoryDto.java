package com.javarush.khmelov.mapper;

import com.javarush.khmelov.model.story.Story;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoryDto {
    Story.Out out(Story entity);

    Story in(Story.In inputDto);
}
