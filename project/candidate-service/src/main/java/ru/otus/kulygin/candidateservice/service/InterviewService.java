package ru.otus.kulygin.candidateservice.service;

import ru.otus.kulygin.candidateservice.vo.InterviewVO;

import java.util.List;

public interface InterviewService {

    boolean existsByCandidateId(String candidateId);

    List<InterviewVO> findAllByCandidateId(String candidateId);

    List<InterviewVO> saveAllInterviews(List<InterviewVO> interviews);

}
