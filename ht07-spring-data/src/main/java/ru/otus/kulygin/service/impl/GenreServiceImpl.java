package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.repository.GenreRepository;
import ru.otus.kulygin.service.GenreService;

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
    @Transactional(readOnly = true)
    public long count() {
        return genreRepository.count();
    }

    @Override
    @Transactional
    public void insert(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> getById(long id) {
        return genreRepository.findById(id).map(genre -> mappingService.map(genre, GenreDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAll() {
        return mappingService.mapAsList(genreRepository.findAll(), GenreDto.class);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!genreRepository.existsById(id)) {
            throw new GenreDoesNotExistException("Genre does not exist");
        }
        genreRepository.deleteById(id);
    }

}
