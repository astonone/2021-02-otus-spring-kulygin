package ru.otus.kulygin.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.kulygin.service.InterviewerService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/interviewer")
public class InterviewerController {

    private final InterviewerService interviewerService;

    public InterviewerController(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }
}
