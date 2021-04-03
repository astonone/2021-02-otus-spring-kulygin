package ru.otus.kulygin.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AuthorDto {

    private Long id;
    private String firstName;
    private String lastName;

}
