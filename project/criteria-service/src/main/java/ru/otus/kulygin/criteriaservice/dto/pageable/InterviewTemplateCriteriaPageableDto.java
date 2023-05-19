package ru.otus.kulygin.criteriaservice.dto.pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kulygin.criteriaservice.dto.InterviewTemplateCriteriaDto;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplateCriteriaPageableDto implements Serializable {

    private long totalSize;
    private int totalPageSize;
    private int currentPageSize;
    private int page;
    private int pageSize;
    private List<InterviewTemplateCriteriaDto> interviewTemplateCriterias;

}
