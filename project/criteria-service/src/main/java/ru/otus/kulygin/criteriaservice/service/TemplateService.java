package ru.otus.kulygin.criteriaservice.service;

import ru.otus.kulygin.criteriaservice.vo.InterviewTemplateVO;

import java.util.List;

public interface TemplateService {

    boolean existsTemplateByCriteriaId(String id);

    List<InterviewTemplateVO> findAllByCriteriaId(String criteriaId);

    List<InterviewTemplateVO> saveAll(List<InterviewTemplateVO> interviews);

}
