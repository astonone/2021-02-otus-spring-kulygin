package ru.otus.kulygin.templateservice.service;

import ru.otus.kulygin.templateservice.vo.InterviewVO;

import java.util.List;

public interface InterviewService {

    boolean existByTemplateId(String templateId);

    List<InterviewVO> findAllByTemplateId(String templateId);

    List<InterviewVO> saveAll(List<InterviewVO> interviews);

}
