package ru.otus.kulygin.criteriaservice.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.criteriaservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.criteriaservice.dto.pageable.InterviewTemplateCriteriaPageableDto;

public interface InterviewTemplateCriteriaService {

    InterviewTemplateCriteriaPageableDto findAll(Pageable pageable);

    InterviewTemplateCriteriaDto save(InterviewTemplateCriteriaDto criteria);

    void deleteById(String id);

    InterviewTemplateCriteria processItem(InterviewTemplateCriteria criteria);

    boolean existById(String id);

}
