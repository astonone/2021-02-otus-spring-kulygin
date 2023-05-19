package ru.otus.kulygin.templateservice.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateDto;
import ru.otus.kulygin.templateservice.dto.pageable.InterviewTemplatePageableDto;

import java.util.List;

public interface InterviewTemplateService {

    InterviewTemplatePageableDto findAll(Pageable pageable);

    InterviewTemplatePageableDto findAll();

    InterviewTemplateDto save(InterviewTemplateDto interviewTemplateDto);

    void deleteById(String id);

    InterviewTemplateDto addCriteria(String templateId, InterviewTemplateCriteriaDto criteria);

    InterviewTemplateDto deleteCriteria(String templateId, String criteriaId);

    InterviewTemplateDto getById(String id);

    boolean existByCriteriaId(String criteriaId);

    List<InterviewTemplateDto> findAllByCriteriaId(String criteriaId);

    List<InterviewTemplateDto> saveAll(List<InterviewTemplateDto> interviews);
}
