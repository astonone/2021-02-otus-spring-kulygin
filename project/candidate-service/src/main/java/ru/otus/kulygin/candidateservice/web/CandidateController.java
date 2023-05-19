package ru.otus.kulygin.candidateservice.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.candidateservice.dto.CandidateDto;
import ru.otus.kulygin.candidateservice.dto.ErrorDto;
import ru.otus.kulygin.candidateservice.exception.CandidateDoesNotExistException;
import ru.otus.kulygin.candidateservice.exception.FileWritingException;
import ru.otus.kulygin.candidateservice.exception.WrongCvFileFormatException;
import ru.otus.kulygin.candidateservice.service.CandidateService;

import static ru.otus.kulygin.candidateservice.enumerations.ApplicationErrorsEnum.*;

@RestController
@RequestMapping(path = "/candidate")
@AllArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (page != null && pageSize != null) {
            return new ResponseEntity<>(candidateService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(candidateService.findAll(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestPart(value = "candidateDto") CandidateDto candidateDto,
                                  @RequestPart(value = "uploadedFile", required = false) MultipartFile uploadedFile) {
        try {
            return new ResponseEntity<>(candidateService.save(candidateDto, uploadedFile), HttpStatus.OK);
        } catch (CandidateDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(CANDIDATE_NOT_FOUND.getCode(), CANDIDATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (FileWritingException e) {
            return new ResponseEntity<>(new ErrorDto(FILE_WRITING_ERROR.getCode(), FILE_WRITING_ERROR.getMessage()), HttpStatus.NOT_FOUND);
        } catch (WrongCvFileFormatException e) {
            return new ResponseEntity<>(new ErrorDto(WRONG_FILE_FORMAT_EXCEPTION.getCode(), WRONG_FILE_FORMAT_EXCEPTION.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            candidateService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(candidateService.getById(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(CANDIDATE_NOT_FOUND.getCode(), CANDIDATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
