package ru.otus.kulygin.criteriaservice.service;

import ru.otus.kulygin.criteriaservice.vo.InterviewVO;

import java.util.List;

public interface InterviewService {

    List<InterviewVO> findAllPlannedByCriteriaId(String criteriaId);

    List<InterviewVO> saveAll(List<InterviewVO> interviews);

}
