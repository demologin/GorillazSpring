package com.javarush.khmelov.model.story;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    Long id;
    Long editorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In {
        @Positive
        Long id;

        @Positive
        Long editorId;

        @NotBlank
        @Size(min = 2, max = 64)
        String title;

        @Size(min = 4, max = 2048)
        String content;

        LocalDateTime created;
        LocalDateTime modified;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out {
        Long id;
        Long editorId;
        String title;
        String content;
        LocalDateTime created;
        LocalDateTime modified;
    }

}
