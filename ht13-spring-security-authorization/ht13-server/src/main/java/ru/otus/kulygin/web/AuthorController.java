package ru.otus.kulygin.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.list.AuthorListDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.service.AuthorService;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.AUTHOR_NOT_FOUND;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.RELATED_ENTITY;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/author")
    public ResponseEntity<?> getAllAuthors() {
        return new ResponseEntity<>(AuthorListDto.builder()
                .authors(authorService.getAll())
                .build(), HttpStatus.OK);
    }

    @PostMapping("/api/author")
    public ResponseEntity<?> save(@RequestBody AuthorDto authorDto) {
        try {
            return new ResponseEntity<>(authorService.save(authorDto), HttpStatus.OK);
        } catch (AuthorDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(AUTHOR_NOT_FOUND.getCode(), AUTHOR_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/author/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable String id) {
        try {
            authorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
