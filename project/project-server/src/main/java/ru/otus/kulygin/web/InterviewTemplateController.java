package ru.otus.kulygin.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.kulygin.service.InterviewTemplateService;

@RestController
@RequestMapping(path = "/api/interview-template")
public class InterviewTemplateController {

    private final InterviewTemplateService interviewTemplateService;

    public InterviewTemplateController(InterviewTemplateService interviewTemplateService) {
        this.interviewTemplateService = interviewTemplateService;
    }
}
