package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.service.InterviewerService;

import java.util.Optional;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.INTERVIEWER_NOT_FOUND;
import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.RELATED_ENTITY;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/interviewer")
public class InterviewerController {

    private final InterviewerService interviewerService;

    public InterviewerController(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInterviewersPageable(@RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(interviewerService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody InterviewerDto interviewerDto) {
        Interviewer forSave = Interviewer.builder().build();
        Optional<InterviewerDto> interviewerById = Optional.empty();
        if (interviewerDto.getId() != null) {
            interviewerById = interviewerService.getById(interviewerDto.getId());
            if (interviewerById.isEmpty()) {
                return new ResponseEntity<>(new ErrorDto(INTERVIEWER_NOT_FOUND.getCode(), INTERVIEWER_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
            }
        }
        forSave.setId(interviewerById.map(InterviewerDto::getId).orElse(null));
        forSave.setFirstName(interviewerDto.getFirstName());
        forSave.setLastName(interviewerDto.getLastName());
        return new ResponseEntity<>(interviewerService.update(forSave), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable String id) {
        try {
            interviewerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
