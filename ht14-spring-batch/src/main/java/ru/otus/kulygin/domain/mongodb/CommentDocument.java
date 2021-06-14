package ru.otus.kulygin.domain.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("comment")
public class CommentDocument {

    @Id
    private String id;

    @Field(name = "commentator_name")
    private String commentatorName;

    @Field(name = "text")
    private String text;

    @DBRef
    private BookDocument book;

}
