package ru.otus.kulygin.dto.pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kulygin.dto.InterviewDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewPageableDto {

    private long totalSize;
    private int totalPageSize;
    private int currentPageSize;
    private int page;
    private int pageSize;
    private List<InterviewDto> interviews;

}
