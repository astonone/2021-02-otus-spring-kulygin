package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;

import java.util.Optional;

public interface InterviewerService {

    InterviewerPageableDto findAll(Pageable pageable);

    InterviewerDto save(Interviewer interviewer);

    Optional<InterviewerDto> getById(String id);

    void deleteById(String id);

}
