package ru.otus.kulygin.templateservice.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.templateservice.dto.ErrorDto;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateDto;
import ru.otus.kulygin.templateservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.templateservice.exception.InterviewTemplateDoesNotExistException;
import ru.otus.kulygin.templateservice.service.InterviewTemplateService;

import java.util.List;

import static ru.otus.kulygin.templateservice.enumerations.ApplicationErrorsEnum.*;

@RestController
@RequestMapping(path = "/interview-template")
@AllArgsConstructor
public class InterviewTemplateController {

    private final InterviewTemplateService interviewTemplateService;

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (page != null && pageSize != null) {
            return new ResponseEntity<>(interviewTemplateService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(interviewTemplateService.findAll(), HttpStatus.OK);
        }
    }

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
        } catch (InterviewTemplateDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_TEMPLATE_NOT_FOUND.getCode(), INTERVIEW_TEMPLATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("criteria/exists/{id}")
    public ResponseEntity<?> existsByCriteriaId(@PathVariable String id) {
        return new ResponseEntity<>(interviewTemplateService.existByCriteriaId(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            interviewTemplateService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{templateId}/criteria")
    public ResponseEntity<?> addCriteria(@PathVariable String templateId, @RequestBody InterviewTemplateCriteriaDto criteria) {
        try {
            return new ResponseEntity<>(interviewTemplateService.addCriteria(templateId, criteria), HttpStatus.OK);
        } catch (InterviewTemplateDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEW_TEMPLATE_NOT_FOUND.getCode(), INTERVIEW_TEMPLATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

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

    @GetMapping("criteria/{criteriaId}")
    public ResponseEntity<?> findAllByCriteriaId(@PathVariable("criteriaId") String criteriaId) {
        return new ResponseEntity<>(interviewTemplateService.findAllByCriteriaId(criteriaId), HttpStatus.OK);
    }

    @PostMapping("list")
    public ResponseEntity<?> saveAll(@RequestBody List<InterviewTemplateDto> interviews) {
        return new ResponseEntity<>(interviewTemplateService.saveAll(interviews), HttpStatus.OK);
    }

}
