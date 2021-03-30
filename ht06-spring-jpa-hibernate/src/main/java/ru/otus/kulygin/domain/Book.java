package ru.otus.kulygin.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "book")
@NamedEntityGraph(name = "genre-author-entity-graph",
        attributeNodes = {@NamedAttributeNode("genre"), @NamedAttributeNode("author")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToOne(targetEntity = Author.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<>();

}
