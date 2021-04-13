package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.service.InterviewerService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/interviewer")
public class InterviewerController {

    private final InterviewerService interviewerService;

    public InterviewerController(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInterviewersPageable(@RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(interviewerService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }
}
