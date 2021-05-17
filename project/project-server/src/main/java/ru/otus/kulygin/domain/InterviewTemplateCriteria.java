package ru.otus.kulygin.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class InterviewTemplateCriteria {

    @Id
    private String id;
    private String name;
    private String positionType;
    private Integer mark = 0;
    private String interviewerComment;

}

