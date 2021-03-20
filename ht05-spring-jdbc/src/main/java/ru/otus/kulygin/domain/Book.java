package ru.otus.kulygin.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    private long id;
    private String title;
    private Author author;
    private Genre genre;

}
