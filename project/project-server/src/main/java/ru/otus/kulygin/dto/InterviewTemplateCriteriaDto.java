package ru.otus.kulygin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplateCriteriaDto {

    private String id;
    private String name;
    private String positionType;
    private Integer mark;
    private String interviewerComment;

}
