package ru.otus.kulygin.domain.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("book")
public class BookDocument {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "author")
    private AuthorDocument author;

    @Field(name = "genre")
    private GenreDocument genre;

}
