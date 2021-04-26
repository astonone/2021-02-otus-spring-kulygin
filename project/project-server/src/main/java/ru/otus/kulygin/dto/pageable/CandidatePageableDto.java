package ru.otus.kulygin.dto.pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kulygin.dto.CandidateDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidatePageableDto {

    private long totalSize;
    private int totalPageSize;
    private int currentPageSize;
    private int page;
    private int pageSize;
    private List<CandidateDto> candidates;

}
