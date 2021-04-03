package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.repository.AuthorRepository;
import ru.otus.kulygin.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final MappingService mappingService;

    public AuthorServiceImpl(AuthorRepository authorRepository, MappingService mappingService) {
        this.authorRepository = authorRepository;
        this.mappingService = mappingService;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return authorRepository.count();
    }

    @Override
    @Transactional
    public void insert(Author author) {
        authorRepository.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> getById(long id) {
        return authorRepository.findById(id).map(author -> mappingService.map(author, AuthorDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return mappingService.mapAsList(authorRepository.findAll(), AuthorDto.class);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorDoesNotExistException("Author does not exist");
        }
        authorRepository.deleteById(id);
    }

}
