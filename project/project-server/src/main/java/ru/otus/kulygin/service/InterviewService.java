package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.InterviewDto;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.pageable.InterviewPageableDto;

public interface InterviewService {

    InterviewPageableDto findAll(Pageable pageable);

    InterviewPageableDto findAll();

    InterviewPageableDto findAllByInterviewStatus(Pageable pageable, String status);

    InterviewDto save(InterviewDto interviewDto);

    void deleteById(String id);

    InterviewDto getById(String id);

    InterviewDto updateCriteria(String interviewId, String criteriaId, Integer mark);

    InterviewDto updateCriteriaComment(String interviewId, String criteriaId, InterviewTemplateCriteriaDto criteria);
}
