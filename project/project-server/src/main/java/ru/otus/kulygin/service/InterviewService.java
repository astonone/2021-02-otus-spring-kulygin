package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;

public interface InterviewService {

    InterviewerPageableDto findAll(Pageable pageable);

}
