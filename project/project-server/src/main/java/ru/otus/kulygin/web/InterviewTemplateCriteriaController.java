package ru.otus.kulygin.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.kulygin.service.InterviewTemplateCriteriaService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/interview-template-criteria")
public class InterviewTemplateCriteriaController {

    private final InterviewTemplateCriteriaService interviewTemplateCriteriaService;

    public InterviewTemplateCriteriaController(InterviewTemplateCriteriaService interviewTemplateCriteriaService) {
        this.interviewTemplateCriteriaService = interviewTemplateCriteriaService;
    }
}
