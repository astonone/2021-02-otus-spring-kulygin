package ru.otus.kulygin.templateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplateCriteriaDto implements Serializable {

    private String id;
    private String name;
    private String positionType;
    private int mark;
    private String interviewerComment;

}
