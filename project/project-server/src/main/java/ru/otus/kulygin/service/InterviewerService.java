package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;

public interface InterviewerService {

    InterviewerPageableDto findAll(Pageable pageable);

    InterviewerDto save(InterviewerDto interviewerDto);

    void deleteById(String id);

}
