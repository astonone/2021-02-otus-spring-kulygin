package ru.otus.kulygin.interviewservice.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.interviewservice.dto.InterviewDto;
import ru.otus.kulygin.interviewservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.interviewservice.dto.pageable.InterviewPageableDto;

import java.util.List;

public interface InterviewService {

    InterviewPageableDto findAll(Pageable pageable);

    InterviewPageableDto findAll();

    InterviewPageableDto findAllByInterviewStatus(Pageable pageable, String status);

    InterviewDto save(InterviewDto interviewDto);

    void deleteById(String id);

    InterviewDto getById(String id);

    InterviewDto updateCriteria(String interviewId, String criteriaId, Integer mark);

    InterviewDto updateCriteriaComment(String interviewId, String criteriaId, InterviewTemplateCriteriaDto criteria);

    boolean existsByCandidateId(String candidateId);

    List<InterviewDto> findAllByCandidateId(String candidateId);

    List<InterviewDto> saveAll(List<InterviewDto> interviews);

    boolean existsByInterviewerId(String interviewerId);

    List<InterviewDto> findAllByInterviewerId(String interviewerId);

    List<InterviewDto> findAllPlannedByCriteriaId(String criteriaId);

    boolean existsByInterviewTemplateId(String templateId);

    List<InterviewDto> findAllByInterviewTemplateIdAndInterviewStatus(String templateId);
}
