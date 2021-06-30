package ru.otus.kulygin.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.UserDto;
import ru.otus.kulygin.exception.SecretKeyException;
import ru.otus.kulygin.exception.UserDoesNotExistException;
import ru.otus.kulygin.exception.UsernameAlreadyExistException;
import ru.otus.kulygin.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        final boolean login = userService.login(userDto);
        if (login) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping("/api/user/authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request) {
        return new ResponseEntity<>(userService.getUserByUsername(request), HttpStatus.OK);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(USER_NOT_FOUND.getCode(), GENRE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(userService.save(userDto), HttpStatus.OK);
        } catch (UsernameAlreadyExistException e) {
            return new ResponseEntity<>(new ErrorDto(USER_EXIST.getCode(), USER_EXIST.getMessage()), HttpStatus.NOT_FOUND);
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (SecretKeyException e) {
            return new ResponseEntity<>(new ErrorDto(SECRET_KEY_ERROR.getCode(), SECRET_KEY_ERROR.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(USER_NOT_FOUND.getCode(), GENRE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
