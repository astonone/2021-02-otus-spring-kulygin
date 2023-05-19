package ru.otus.kulygin.criteriaservice.web;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.criteriaservice.dto.BatchInfoDto;
import ru.otus.kulygin.criteriaservice.dto.ErrorDto;
import ru.otus.kulygin.criteriaservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.criteriaservice.exception.FileWritingException;
import ru.otus.kulygin.criteriaservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.criteriaservice.service.BatchService;
import ru.otus.kulygin.criteriaservice.service.InterviewTemplateCriteriaService;

import static ru.otus.kulygin.criteriaservice.enumeration.ApplicationErrorsEnum.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/interview-template-criteria")
public class InterviewTemplateCriteriaController {

    private final InterviewTemplateCriteriaService interviewTemplateCriteriaService;
    private final BatchService batchService;

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam("page") Integer page,
                                            @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(interviewTemplateCriteriaService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewTemplateCriteriaDto criteriaDto) {
        try {
            return new ResponseEntity<>(interviewTemplateCriteriaService.save(criteriaDto), HttpStatus.OK);
        } catch (InterviewTemplateCriteriaDoesNotExist e) {
            return new ResponseEntity<>(new ErrorDto(CRITERIA_NOT_FOUND.getCode(), CRITERIA_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
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

    @PostMapping("job/import-criterias")
    public ResponseEntity<?> importCriterias(@RequestPart(value = "uploadedFile") MultipartFile uploadedFile) {
        try {
            batchService.startImportCriteriasJob(uploadedFile);
        } catch (FileWritingException e) {
            return new ResponseEntity<>(new ErrorDto(INVALID_FILE_FOR_BATCH.getCode(), INVALID_FILE_FOR_BATCH.getMessage()), HttpStatus.NOT_FOUND);
        } catch (JobInstanceAlreadyExistsException e) {
            return new ResponseEntity<>(new ErrorDto(JOB_INSTANCE_ALREADY_EXISTS.getCode(), JOB_INSTANCE_ALREADY_EXISTS.getMessage()), HttpStatus.NOT_FOUND);
        } catch (NoSuchJobException e) {
            return new ResponseEntity<>(new ErrorDto(JOB_DOES_NOT_EXIST.getCode(), JOB_DOES_NOT_EXIST.getMessage()), HttpStatus.NOT_FOUND);
        } catch (JobParametersInvalidException e) {
            return new ResponseEntity<>(new ErrorDto(JOB_PARAMETERS_INVALID.getCode(), JOB_PARAMETERS_INVALID.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new BatchInfoDto("Batch job successfully started"), HttpStatus.OK);
    }

    @GetMapping("job/import-criterias")
    public ResponseEntity<?> getImportJobInfo() {
        try {
            return new ResponseEntity<>(new BatchInfoDto(batchService.getLastJobExecutionInfo()), HttpStatus.OK);
        } catch (NoSuchJobExecutionException e) {
            return new ResponseEntity<>(new ErrorDto(JOB_INSTANCE_DOES_NOT_EXIST.getCode(), JOB_INSTANCE_DOES_NOT_EXIST.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("exists/{id}")
    public ResponseEntity<?> existById(@PathVariable String id) {
        return new ResponseEntity<>(interviewTemplateCriteriaService.existById(id), HttpStatus.OK);
    }

}
