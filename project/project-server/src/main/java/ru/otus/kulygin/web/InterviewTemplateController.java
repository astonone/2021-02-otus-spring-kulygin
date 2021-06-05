package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.InterviewTemplateDto;
import ru.otus.kulygin.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.exception.InterviewTemplateDoesNotExistException;
import ru.otus.kulygin.service.InterviewTemplateService;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.*;

@RestController
@RequestMapping(path = "/api/interview-template")
public class InterviewTemplateController {

    private final InterviewTemplateService interviewTemplateService;

    public InterviewTemplateController(InterviewTemplateService interviewTemplateService) {
        this.interviewTemplateService = interviewTemplateService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (page != null && pageSize != null) {
            return new ResponseEntity<>(interviewTemplateService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(interviewTemplateService.findAll(), HttpStatus.OK);
        }
    }

    @Secured("ROLE_DEVELOPER")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewTemplateDto templateDto) {
        try {
            return new ResponseEntity<>(interviewTemplateService.save(templateDto), HttpStatus.OK);
        } catch (InterviewTemplateDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_TEMPLATE_NOT_FOUND.getCode(), INTERVIEW_TEMPLATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(interviewTemplateService.getById(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_TEMPLATE_NOT_FOUND.getCode(), INTERVIEW_TEMPLATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_DEVELOPER")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            interviewTemplateService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_DEVELOPER")
    @PostMapping("{templateId}/criteria")
    public ResponseEntity<?> addCriteria(@PathVariable String templateId, @RequestBody InterviewTemplateCriteriaDto criteria) {
        try {
            return new ResponseEntity<>(interviewTemplateService.addCriteria(templateId, criteria), HttpStatus.OK);
        } catch (InterviewTemplateDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_TEMPLATE_NOT_FOUND.getCode(), INTERVIEW_TEMPLATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_DEVELOPER")
    @DeleteMapping("{templateId}/criteria/{criteriaId}")
    public ResponseEntity<?> deleteCriteria(@PathVariable String templateId, @PathVariable String criteriaId) {
        try {
            return new ResponseEntity<>(interviewTemplateService.deleteCriteria(templateId, criteriaId), HttpStatus.OK);
        } catch (InterviewTemplateDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_TEMPLATE_NOT_FOUND.getCode(), INTERVIEW_TEMPLATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InterviewTemplateCriteriaDoesNotExist e) {
            return new ResponseEntity<>(new ErrorDto(CRITERIA_NOT_FOUND.getCode(), CRITERIA_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
