package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final MappingService mappingService;

    public AuthorServiceImpl(AuthorDao authorDao, MappingService mappingService) {
        this.authorDao = authorDao;
        this.mappingService = mappingService;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return authorDao.count();
    }

    @Override
    @Transactional
    public void insert(Author author) {
        authorDao.insert(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> getById(long id) {
        return authorDao.getById(id).map(author -> mappingService.map(author, AuthorDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return mappingService.mapAsList(authorDao.getAll(), AuthorDto.class);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        try {
            authorDao.deleteById(id);
        } catch (AuthorDoesNotExistException e) {
            throw new AuthorDoesNotExistException(e);
        } catch (Exception e) {
            throw new RelatedEntityException("Seems Author has related book: ", e);
        }
    }

}
