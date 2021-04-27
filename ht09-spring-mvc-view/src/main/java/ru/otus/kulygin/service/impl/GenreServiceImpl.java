package ru.otus.kulygin.service.impl;

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

    @Override
    public void save(GenreDto genreDto) {
        genreRepository.save(mappingService.map(genreDto, Genre.class));
    }

    @Override
    public Optional<GenreDto> getById(String id) {
        return genreRepository.findById(id).map(genre -> mappingService.map(genre, GenreDto.class));
    }

    @Override
    public List<GenreDto> getAll() {
        return mappingService.mapAsList(genreRepository.findAll(), GenreDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (!genreRepository.existsById(id)) {
            throw new GenreDoesNotExistException("Genre does not exist");
        }
        genreRepository.deleteById(id);
    }

}
