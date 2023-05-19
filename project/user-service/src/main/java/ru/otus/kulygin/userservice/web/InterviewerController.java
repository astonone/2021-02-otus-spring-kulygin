package ru.otus.kulygin.userservice.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.userservice.dto.ErrorDto;
import ru.otus.kulygin.userservice.dto.InterviewerDto;
import ru.otus.kulygin.userservice.exception.InterviewerDoesNotExistException;
import ru.otus.kulygin.userservice.exception.SecretKeyException;
import ru.otus.kulygin.userservice.exception.UsernameAlreadyExistException;
import ru.otus.kulygin.userservice.service.InterviewerService;

import static ru.otus.kulygin.userservice.enumerations.ApplicationErrorsEnum.*;

@RestController
@RequestMapping(path = "/interviewer")
@AllArgsConstructor
public class InterviewerController {

    private final InterviewerService interviewerService;

    @GetMapping("username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return new ResponseEntity<>(interviewerService.findByUsername(username), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody InterviewerDto interviewerDto) {
        final String id = interviewerService.login(interviewerDto);
        if (id != null) {
            return new ResponseEntity<>(InterviewerDto.builder()
                    .id(id)
                    .build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(interviewerService.getById(id), HttpStatus.OK);
        } catch (InterviewerDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEWER_NOT_FOUND.getCode(), INTERVIEWER_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPageable(@RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (page != null && pageSize != null) {
            return new ResponseEntity<>(interviewerService.findAll(PageRequest.of(page, pageSize)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(interviewerService.findAll(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InterviewerDto interviewerDto) {
        try {
            return new ResponseEntity<>(interviewerService.save(interviewerDto), HttpStatus.OK);
        } catch (InterviewerDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(INTERVIEWER_NOT_FOUND.getCode(), INTERVIEWER_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (UsernameAlreadyExistException e) {
            return new ResponseEntity<>(new ErrorDto(USER_EXIST.getCode(), USER_EXIST.getMessage()), HttpStatus.NOT_FOUND);
        } catch (SecretKeyException e) {
            return new ResponseEntity<>(new ErrorDto(SECRET_KEY_ERROR.getCode(), SECRET_KEY_ERROR.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            interviewerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
