package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.pageable.InterviewTemplateCriteriaPageableDto;

public interface InterviewTemplateCriteriaService {

    InterviewTemplateCriteriaPageableDto findAll(Pageable pageable);

    InterviewTemplateCriteriaDto save(InterviewTemplateCriteriaDto criteria);

    void deleteById(String id);

}
