package ru.otus.kulygin.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GenreDto {

    private Long id;
    private String name;

}
