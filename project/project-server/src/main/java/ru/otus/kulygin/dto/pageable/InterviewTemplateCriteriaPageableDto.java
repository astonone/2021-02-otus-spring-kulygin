package ru.otus.kulygin.dto.pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplateCriteriaPageableDto {

    private long totalSize;
    private int totalPageSize;
    private int currentPageSize;
    private int page;
    private int pageSize;
    private List<InterviewTemplateCriteriaDto> interviewTemplateCriterias;

}
