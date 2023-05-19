package ru.otus.kulygin.interviewservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplateCriteriaVO implements Serializable {

    private String id;
    private String name;
    private String positionType;
    private int mark;
    private String interviewerComment;

}
