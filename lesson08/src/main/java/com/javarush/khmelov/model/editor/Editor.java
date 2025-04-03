package com.javarush.khmelov.model.editor;

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
public class Editor {

    Long id;
    String login;
    String password;
    String firstname;
    String lastname;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In {
        @Positive
        Long id;
        @Size(min = 2, max = 64)
        String login;
        @Size(min = 8, max = 128)
        String password;
        @Size(min = 2, max = 64)
        String firstname;
        @Size(min = 2, max = 64)
        String lastname;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out {
        Long id;
        String login;
        String password;
        String firstname;
        String lastname;
    }

}
