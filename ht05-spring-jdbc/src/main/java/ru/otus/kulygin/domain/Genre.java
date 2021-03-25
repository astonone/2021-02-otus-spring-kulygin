package ru.otus.kulygin.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Genre {

    private long id;
    private String name;

}
