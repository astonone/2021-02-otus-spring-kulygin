package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.InterviewDto;
import ru.otus.kulygin.dto.pageable.InterviewPageableDto;

public interface InterviewService {

    InterviewPageableDto findAll(Pageable pageable);

    InterviewPageableDto findAll();

    InterviewDto save(InterviewDto interviewDto);

    void deleteById(String id);

}
