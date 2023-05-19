package ru.otus.kulygin.interviewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplate implements Serializable {

    private String id;
    private String positionName;
    private List<InterviewTemplateCriteria> criterias;

}
