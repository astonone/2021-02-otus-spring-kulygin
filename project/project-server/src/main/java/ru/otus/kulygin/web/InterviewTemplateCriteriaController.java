package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.service.InterviewTemplateCriteriaService;

import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewTemplateCriteriaDto criteriaDto) {
        InterviewTemplateCriteria forSave = InterviewTemplateCriteria.builder().build();
        Optional<InterviewTemplateCriteriaDto> criteriaById = Optional.empty();
        if (criteriaDto.getId() != null) {
            criteriaById = interviewTemplateCriteriaService.getById(criteriaDto.getId());
            if (criteriaById.isEmpty()) {
                return new ResponseEntity<>(new ErrorDto(CRITERIA_NOT_FOUND.getCode(), CRITERIA_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
            }
        }
        forSave.setId(criteriaById.map(InterviewTemplateCriteriaDto::getId).orElse(null));
        forSave.setName(criteriaDto.getName());
        forSave.setPositionType(criteriaDto.getPositionType());
        forSave.setMark(criteriaDto.getMark());
        forSave.setInterviewerComment(criteriaDto.getInterviewerComment());
        return new ResponseEntity<>(interviewTemplateCriteriaService.save(forSave), HttpStatus.OK);
    }

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
