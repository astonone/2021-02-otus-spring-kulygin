package ru.otus.kulygin.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.exception.InterviewerDoesNotExistException;
import ru.otus.kulygin.exception.SecretKeyException;
import ru.otus.kulygin.exception.UserDoesNotExistException;
import ru.otus.kulygin.exception.UsernameAlreadyExistException;
import ru.otus.kulygin.service.InterviewerService;

import javax.servlet.http.HttpServletRequest;

import static ru.otus.kulygin.enumeration.ApplicationErrorsEnum.*;

@RestController
@RequestMapping(path = "/api/interviewer")
public class InterviewerController {

    private final InterviewerService interviewerService;

    public InterviewerController(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody InterviewerDto interviewerDto) {
        final boolean login = interviewerService.login(interviewerDto);
        if (login) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping("authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request) {
        return new ResponseEntity<>(interviewerService.getUserByUsername(request), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(interviewerService.getById(id), HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
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

    @Secured("ROLE_DEVELOPER")
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

    @Secured("ROLE_DEVELOPER")
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
