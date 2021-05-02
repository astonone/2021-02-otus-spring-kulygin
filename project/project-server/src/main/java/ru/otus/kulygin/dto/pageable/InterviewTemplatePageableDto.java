package ru.otus.kulygin.dto.pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kulygin.dto.InterviewTemplateDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTemplatePageableDto {

    private long totalSize;
    private int totalPageSize;
    private int currentPageSize;
    private int page;
    private int pageSize;
    private List<InterviewTemplateDto> templates;

}
