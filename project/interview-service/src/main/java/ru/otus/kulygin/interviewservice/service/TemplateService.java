package ru.otus.kulygin.interviewservice.service;

import ru.otus.kulygin.interviewservice.vo.InterviewTemplateVO;

public interface TemplateService {

    InterviewTemplateVO getInterviewTemplateById(String templateId);

}
