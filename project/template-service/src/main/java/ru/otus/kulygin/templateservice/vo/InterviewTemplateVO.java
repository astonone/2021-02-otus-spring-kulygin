package ru.otus.kulygin.templateservice.vo;

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
public class InterviewTemplateVO implements Serializable {

    private String id;
    private String positionName;
    private List<InterviewTemplateCriteriaVO> criterias;

}
