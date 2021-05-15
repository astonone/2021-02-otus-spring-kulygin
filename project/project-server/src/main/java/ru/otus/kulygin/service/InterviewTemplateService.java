package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.InterviewTemplateDto;
import ru.otus.kulygin.dto.pageable.InterviewTemplatePageableDto;

public interface InterviewTemplateService {

    InterviewTemplatePageableDto findAll(Pageable pageable);

    InterviewTemplatePageableDto findAll();

    InterviewTemplateDto save(InterviewTemplateDto interviewTemplateDto);

    void deleteById(String id);

    InterviewTemplateDto addCriteria(String templateId, InterviewTemplateCriteriaDto criteria);

    InterviewTemplateDto deleteCriteria(String templateId, String criteriaId);

    InterviewTemplateDto getById(String id);

}
