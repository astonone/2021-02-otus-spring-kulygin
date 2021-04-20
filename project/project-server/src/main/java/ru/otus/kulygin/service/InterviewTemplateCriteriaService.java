package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.pageable.InterviewTemplateCriteriaPageableDto;

import java.util.Optional;

public interface InterviewTemplateCriteriaService {

    InterviewTemplateCriteriaPageableDto findAll(Pageable pageable);

    InterviewTemplateCriteriaDto save(InterviewTemplateCriteria criteria);

    Optional<InterviewTemplateCriteriaDto> getById(String id);

    void deleteById(String id);

}
