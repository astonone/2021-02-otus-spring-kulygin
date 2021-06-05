package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;

import javax.servlet.http.HttpServletRequest;

public interface InterviewerService {

    InterviewerPageableDto findAll(Pageable pageable);

    InterviewerPageableDto findAll();

    InterviewerDto getById(String id);

    InterviewerDto save(InterviewerDto interviewerDto);

    void deleteById(String id);

    boolean login(InterviewerDto interviewerDto);

    InterviewerDto getUserByUsername(HttpServletRequest request);

}
