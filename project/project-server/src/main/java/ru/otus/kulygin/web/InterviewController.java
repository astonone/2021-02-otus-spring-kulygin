package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewDto;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.exception.InterviewDecisionException;
import ru.otus.kulygin.exception.InterviewDoesNotExistException;
import ru.otus.kulygin.exception.InterviewStatusException;
import ru.otus.kulygin.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.service.InterviewService;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.*;

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

    @GetMapping("status")
    public ResponseEntity<?> getAllByStatus(@RequestParam("status") String status,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(interviewService.findAllByInterviewStatus(PageRequest.of(page, pageSize), status), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(interviewService.getById(id), HttpStatus.OK);
        } catch (InterviewDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_NOT_FOUND.getCode(), INTERVIEW_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewDto interviewDto) {
        try {
            return new ResponseEntity<>(interviewService.save(interviewDto), HttpStatus.OK);
        } catch (InterviewDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_NOT_FOUND.getCode(), INTERVIEW_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InterviewStatusException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_STATUS_EXCEPTION.getCode(), INTERVIEW_STATUS_EXCEPTION.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InterviewDecisionException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_DECISION_EXCEPTION.getCode(), INTERVIEW_DECISION_EXCEPTION.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        interviewService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured("ROLE_DEVELOPER")
    @PostMapping("{interviewId}/criteria/{criteriaId}")
    public ResponseEntity<?> updateCriteria(@PathVariable("interviewId") String interviewId,
                                            @PathVariable("criteriaId") String criteriaId,
                                            @RequestParam("mark") Integer mark) {
        try {
            return new ResponseEntity<>(interviewService.updateCriteria(interviewId, criteriaId, mark), HttpStatus.OK);
        } catch (InterviewDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_NOT_FOUND.getCode(), INTERVIEW_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InterviewTemplateCriteriaDoesNotExist e) {
            return new ResponseEntity<>(new ErrorDto(CRITERIA_NOT_FOUND.getCode(), CRITERIA_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_DEVELOPER")
    @PostMapping("{interviewId}/criteria/{criteriaId}/comment")
    public ResponseEntity<?> updateCriteriaComment(@PathVariable("interviewId") String interviewId,
                                                   @PathVariable("criteriaId") String criteriaId,
                                                   @RequestBody InterviewTemplateCriteriaDto criteria) {
        try {
            return new ResponseEntity<>(interviewService.updateCriteriaComment(interviewId, criteriaId, criteria), HttpStatus.OK);
        } catch (InterviewDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_NOT_FOUND.getCode(), INTERVIEW_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InterviewTemplateCriteriaDoesNotExist e) {
            return new ResponseEntity<>(new ErrorDto(CRITERIA_NOT_FOUND.getCode(), CRITERIA_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
