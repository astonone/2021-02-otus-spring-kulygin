package ru.otus.kulygin.interviewservice.service;

import ru.otus.kulygin.interviewservice.vo.CandidateVO;

public interface CandidateService {

    CandidateVO getCandidateById(String candidateId);

}
