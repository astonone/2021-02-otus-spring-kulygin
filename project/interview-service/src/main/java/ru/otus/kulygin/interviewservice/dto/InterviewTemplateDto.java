package ru.otus.kulygin.interviewservice.dto;

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
public class InterviewTemplateDto implements Serializable {

    private String id;
    private String positionName;
    private List<InterviewTemplateCriteriaDto> criterias;

}
