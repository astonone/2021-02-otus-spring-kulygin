package ru.otus.kulygin.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Author {

    private long id;
    private String firstName;
    private String lastName;

}
