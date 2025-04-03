package com.javarush.khmelov.model.note;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {


    Long id;
    Long storyId;
    String content;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In {
        @Positive
        Long id;

        @Positive
        Long storyId;

        @Size(min = 2, max = 2048)
        String content;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out {
        Long id;
        Long storyId;
        String content;
    }

}
