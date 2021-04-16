package ru.otus.kulygin.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

    private String id;
    private String name;

}
