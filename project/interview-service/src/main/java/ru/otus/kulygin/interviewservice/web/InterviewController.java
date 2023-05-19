package ru.otus.kulygin.interviewservice.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.interviewservice.dto.ErrorDto;
import ru.otus.kulygin.interviewservice.dto.InterviewDto;
import ru.otus.kulygin.interviewservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.interviewservice.exception.InterviewDecisionException;
import ru.otus.kulygin.interviewservice.exception.InterviewDoesNotExistException;
import ru.otus.kulygin.interviewservice.exception.InterviewStatusException;
import ru.otus.kulygin.interviewservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.interviewservice.service.InterviewService;

import java.util.List;

import static ru.otus.kulygin.interviewservice.enumerations.ApplicationErrorsEnum.*;

@RestController
@RequestMapping(path = "/interview")
@AllArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

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

    @GetMapping("candidate/exist/{candidateId}")
    public ResponseEntity<?> existByCandidateId(@PathVariable("candidateId") String candidateId) {
        return new ResponseEntity<>(interviewService.existsByCandidateId(candidateId), HttpStatus.OK);
    }

    @GetMapping("candidate/{candidateId}")
    public ResponseEntity<?> findAllByCandidateId(@PathVariable("candidateId") String candidateId) {
        return new ResponseEntity<>(interviewService.findAllByCandidateId(candidateId), HttpStatus.OK);
    }

    @GetMapping("interviewer/exist/{interviewerId}")
    public ResponseEntity<?> existByInterviewerId(@PathVariable("interviewerId") String interviewerId) {
        return new ResponseEntity<>(interviewService.existsByInterviewerId(interviewerId), HttpStatus.OK);
    }

    @GetMapping("template/exist/{templateId}")
    public ResponseEntity<?> existByTemplateId(@PathVariable("templateId") String templateId) {
        return new ResponseEntity<>(interviewService.existsByInterviewTemplateId(templateId), HttpStatus.OK);
    }

    @GetMapping("interviewer/{interviewerId}")
    public ResponseEntity<?> findAllByInterviewerId(@PathVariable("interviewerId") String interviewerId) {
        return new ResponseEntity<>(interviewService.findAllByInterviewerId(interviewerId), HttpStatus.OK);
    }

    @GetMapping("template/{templateId}/planned")
    public ResponseEntity<?> findAllByTemplateId(@PathVariable("templateId") String templateId) {
        return new ResponseEntity<>(interviewService.findAllByInterviewTemplateIdAndInterviewStatus(templateId), HttpStatus.OK);
    }

    @GetMapping("criteria/{criteriaId}/planned")
    public ResponseEntity<?> findAllByCriteriaId(@PathVariable("criteriaId") String criteriaId) {
        return new ResponseEntity<>(interviewService.findAllPlannedByCriteriaId(criteriaId), HttpStatus.OK);
    }

    @PostMapping("list")
    public ResponseEntity<?> saveAll(@RequestBody List<InterviewDto> interviewDto) {
        return new ResponseEntity<>(interviewService.saveAll(interviewDto), HttpStatus.OK);
    }

}
