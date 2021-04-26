package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.domain.Candidate;
import ru.otus.kulygin.dto.CandidateDto;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.exception.FileWritingException;
import ru.otus.kulygin.exception.WrongCvFileFormatException;
import ru.otus.kulygin.service.CandidateService;

import java.util.Optional;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.*;

@RestController
@RequestMapping(path = "/api/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam("page") Integer page,
                                            @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(candidateService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestPart(value = "candidateDto") CandidateDto candidateDto,
                                  @RequestPart(value = "uploadedFile", required = false) MultipartFile uploadedFile) {
        Candidate forSave = Candidate.builder().build();
        Optional<CandidateDto> candidateById = Optional.empty();
        if (candidateDto.getId() != null) {
            candidateById = candidateService.getById(candidateDto.getId());
            if (candidateById.isEmpty()) {
                return new ResponseEntity<>(new ErrorDto(CANDIDATE_NOT_FOUND.getCode(), CANDIDATE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
            }
        }
        forSave.setId(candidateById.map(CandidateDto::getId).orElse(null));
        forSave.setFirstName(candidateDto.getFirstName());
        forSave.setLastName(candidateDto.getLastName());
        forSave.setClaimingPosition(candidateDto.getClaimingPosition());
        forSave.setInterviewerComment(candidateDto.getInterviewerComment());
        try {
            return new ResponseEntity<>(candidateService.save(forSave, uploadedFile), HttpStatus.OK);
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

}
