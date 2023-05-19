package ru.otus.kulygin.userservice.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.userservice.domain.Interviewer;
import ru.otus.kulygin.userservice.dto.InterviewerDto;
import ru.otus.kulygin.userservice.dto.pageable.InterviewerPageableDto;

public interface InterviewerService {

    InterviewerPageableDto findAll(Pageable pageable);

    InterviewerPageableDto findAll();

    InterviewerDto getById(String id);

    InterviewerDto save(InterviewerDto interviewerDto);

    void deleteById(String id);

    String login(InterviewerDto interviewerDto);

    Interviewer findByUsername(String username);

}
