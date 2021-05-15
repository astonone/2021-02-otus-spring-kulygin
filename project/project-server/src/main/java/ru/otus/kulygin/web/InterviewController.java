package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewDto;
import ru.otus.kulygin.exception.InterviewDoesNotExistException;
import ru.otus.kulygin.service.InterviewService;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.INTERVIEW_NOT_FOUND;

@RestController
@RequestMapping(path = "/api/interview")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (page != null && pageSize != null) {
            return new ResponseEntity<>(interviewService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(interviewService.findAll(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewDto interviewDto) {
        try {
            return new ResponseEntity<>(interviewService.save(interviewDto), HttpStatus.OK);
        } catch (InterviewDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_NOT_FOUND.getCode(), INTERVIEW_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        interviewService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
