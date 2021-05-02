package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.exception.InterviewerDoesNotExistException;
import ru.otus.kulygin.service.InterviewerService;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.INTERVIEWER_NOT_FOUND;
import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.RELATED_ENTITY;

@RestController
@RequestMapping(path = "/api/interviewer")
public class InterviewerController {

    private final InterviewerService interviewerService;

    public InterviewerController(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam("page") Integer page,
                                            @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(interviewerService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewerDto interviewerDto) {
        try {
            return new ResponseEntity<>(interviewerService.save(interviewerDto), HttpStatus.OK);
        } catch (InterviewerDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEWER_NOT_FOUND.getCode(), INTERVIEWER_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            interviewerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
