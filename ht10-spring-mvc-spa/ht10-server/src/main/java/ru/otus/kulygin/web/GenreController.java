package ru.otus.kulygin.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.dto.list.GenreListDto;
import ru.otus.kulygin.service.GenreService;

import java.util.Optional;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.GENRE_NOT_FOUND;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.RELATED_ENTITY;

@RestController
@RequestMapping(path = "/api/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new GenreListDto(genreService.getAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GenreDto genreDto) {
        Genre forSave = Genre.builder().build();
        Optional<GenreDto> genreById = Optional.empty();
        if (genreDto.getId() != null) {
            genreById = genreService.getById(genreDto.getId());
            if (genreById.isEmpty()) {
                return new ResponseEntity<>(new ErrorDto(GENRE_NOT_FOUND.getCode(), GENRE_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
            }
        }
        forSave.setId(genreById.map(GenreDto::getId).orElse(null));
        forSave.setName(genreDto.getName());
        return new ResponseEntity<>(genreService.save(forSave), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            genreService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorDto(RELATED_ENTITY.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
