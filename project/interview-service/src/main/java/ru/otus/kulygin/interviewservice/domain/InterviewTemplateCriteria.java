package ru.otus.kulygin.interviewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplateCriteria implements Serializable {

    private String id;
    private String name;
    private String positionType;
    private int mark = 0;
    private String interviewerComment;

}

