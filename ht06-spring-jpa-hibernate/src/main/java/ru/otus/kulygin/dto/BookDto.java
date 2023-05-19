package ru.otus.kulygin.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookDto {

    private Long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;
    private List<CommentDto> comments;

}
