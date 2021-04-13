package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;

public interface InterviewerService {

    InterviewerPageableDto findAll(Pageable pageable);

}
