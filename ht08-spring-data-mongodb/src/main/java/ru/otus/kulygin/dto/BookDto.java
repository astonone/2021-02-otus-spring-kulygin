package ru.otus.kulygin.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;
    private List<CommentDto> comments;

}
