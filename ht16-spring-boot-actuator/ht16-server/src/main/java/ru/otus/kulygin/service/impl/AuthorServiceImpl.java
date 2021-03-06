package ru.otus.kulygin.service.impl;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.repository.AuthorRepository;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

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
    public long count() {
        return authorRepository.count();
    }

    @Secured("ROLE_ADMIN")
    @Override
    public AuthorDto save(AuthorDto authorDto) {
        Author forSave = Author.builder().build();
        Optional<Author> authorById = Optional.empty();
        if (authorDto.getId() != null) {
            authorById = authorRepository.findById(authorDto.getId());
            if (authorById.isEmpty()) {
                throw new AuthorDoesNotExistException();
            }
        }
        forSave.setId(authorById.map(Author::getId).orElse(null));
        forSave.setFirstName(authorDto.getFirstName());
        forSave.setLastName(authorDto.getLastName());
        return mappingService.map(authorRepository.save(forSave), AuthorDto.class);
    }

    @Override
    public Optional<AuthorDto> getById(String id) {
        return authorRepository.findById(id).map(author -> mappingService.map(author, AuthorDto.class));
    }

    @Override
    public List<AuthorDto> getAll() {
        return mappingService.mapAsList(authorRepository.findAll(), AuthorDto.class);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public void deleteById(String id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorDoesNotExistException("Author does not exist");
        }
        authorRepository.deleteById(id);
    }

}
