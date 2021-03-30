package ru.otus.kulygin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "book")
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commentator_name")
    private String commentatorName;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JsonIgnore
    private Book book;
}
