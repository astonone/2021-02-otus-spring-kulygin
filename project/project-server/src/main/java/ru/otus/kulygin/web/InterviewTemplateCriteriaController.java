package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.service.InterviewTemplateCriteriaService;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.CRITERIA_NOT_FOUND;
import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.RELATED_ENTITY;

@RestController
@RequestMapping(path = "/api/interview-template-criteria")
public class InterviewTemplateCriteriaController {

    private final InterviewTemplateCriteriaService interviewTemplateCriteriaService;

    public InterviewTemplateCriteriaController(InterviewTemplateCriteriaService interviewTemplateCriteriaService) {
        this.interviewTemplateCriteriaService = interviewTemplateCriteriaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam("page") Integer page,
                                            @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(interviewTemplateCriteriaService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }

    @Secured("ROLE_DEVELOPER")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewTemplateCriteriaDto criteriaDto) {
        try {
            return new ResponseEntity<>(interviewTemplateCriteriaService.save(criteriaDto), HttpStatus.OK);
        } catch (InterviewTemplateCriteriaDoesNotExist e) {
            return new ResponseEntity<>(new ErrorDto(CRITERIA_NOT_FOUND.getCode(), CRITERIA_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_DEVELOPER")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            interviewTemplateCriteriaService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
