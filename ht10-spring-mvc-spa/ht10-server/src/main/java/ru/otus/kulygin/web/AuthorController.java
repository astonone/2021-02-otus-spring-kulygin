package ru.otus.kulygin.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.list.AuthorListDto;
import ru.otus.kulygin.service.AuthorService;

import java.util.Optional;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.AUTHOR_NOT_FOUND;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.RELATED_ENTITY;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        return new ResponseEntity<>(AuthorListDto.builder()
                .authors(authorService.getAll())
                .build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AuthorDto authorDto) {
        Author forSave = Author.builder().build();
        Optional<AuthorDto> authorById = Optional.empty();
        if (authorDto.getId() != null) {
            authorById = authorService.getById(authorDto.getId());
            if (authorById.isEmpty()) {
                return new ResponseEntity<>(new ErrorDto(AUTHOR_NOT_FOUND.getCode(), AUTHOR_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
            }
        }
        forSave.setId(authorById.map(AuthorDto::getId).orElse(null));
        forSave.setFirstName(authorDto.getFirstName());
        forSave.setLastName(authorDto.getLastName());
        return new ResponseEntity<>(authorService.save(forSave), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable String id) {
        try {
            authorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
