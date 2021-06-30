package ru.otus.kulygin.service.impl;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.repository.GenreRepository;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    public GenreServiceImpl(GenreRepository genreRepository, MappingService mappingService) {
        this.genreRepository = genreRepository;
        this.mappingService = mappingService;
    }

    @Override
    public long count() {
        return genreRepository.count();
    }

    @Secured("ROLE_ADMIN")
    @Override
    public GenreDto save(GenreDto genreDto) {
        Genre forSave = Genre.builder().build();
        Optional<Genre> genreById = Optional.empty();
        if (genreDto.getId() != null) {
            genreById = genreRepository.findById(genreDto.getId());
            if (genreById.isEmpty()) {
                throw new GenreDoesNotExistException();
            }
        }
        forSave.setId(genreById.map(Genre::getId).orElse(null));
        forSave.setName(genreDto.getName());
        return mappingService.map(genreRepository.save(forSave), GenreDto.class);
    }

    @Override
    public Optional<GenreDto> getById(String id) {
        return genreRepository.findById(id).map(genre -> mappingService.map(genre, GenreDto.class));
    }

    @Override
    public List<GenreDto> getAll() {
        return mappingService.mapAsList(genreRepository.findAll(), GenreDto.class);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public void deleteById(String id) {
        if (!genreRepository.existsById(id)) {
            throw new GenreDoesNotExistException("Genre does not exist");
        }
        genreRepository.deleteById(id);
    }

}
