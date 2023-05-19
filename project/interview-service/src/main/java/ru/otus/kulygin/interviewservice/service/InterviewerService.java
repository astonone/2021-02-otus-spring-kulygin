package ru.otus.kulygin.interviewservice.service;

import ru.otus.kulygin.interviewservice.vo.InterviewerVO;

public interface InterviewerService {

    InterviewerVO getInterviewerById(String interviewerId);

}
