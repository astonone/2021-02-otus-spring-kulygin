package ru.otus.kulygin.userservice.service;

import ru.otus.kulygin.userservice.vo.InterviewVO;

import java.util.List;

public interface InterviewService {

    boolean existsByInterviewerId(String interviewerId);

    List<InterviewVO> findAllByInterviewerId(String candidateId);

    List<InterviewVO> saveAllInterviews(List<InterviewVO> interviews);

}
