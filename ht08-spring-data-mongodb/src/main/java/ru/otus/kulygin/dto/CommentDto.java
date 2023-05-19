package ru.otus.kulygin.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String id;
    private String commentatorName;
    private String text;

}
