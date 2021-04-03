package ru.otus.kulygin.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentDto {

    private Long id;
    private String commentatorName;
    private String text;

}
