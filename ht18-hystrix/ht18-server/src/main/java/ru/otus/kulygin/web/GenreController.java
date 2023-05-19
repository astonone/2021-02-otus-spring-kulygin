package ru.otus.kulygin.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.dto.list.GenreListDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.service.GenreService;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.GENRE_NOT_FOUND;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.RELATED_ENTITY;

@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new GenreListDto(genreService.getAll()), HttpStatus.OK);
    }


    @PostMapping("/api/genre")
    public ResponseEntity<?> save(@RequestBody GenreDto genreDto) {
        try {
            return new ResponseEntity<>(genreService.save(genreDto), HttpStatus.OK);
        } catch (GenreDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(GENRE_NOT_FOUND.getCode(), GENRE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/genre/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            genreService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
