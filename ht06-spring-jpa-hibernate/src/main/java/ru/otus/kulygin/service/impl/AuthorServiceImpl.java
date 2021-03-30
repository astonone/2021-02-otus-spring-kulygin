package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public long count() {
        return authorDao.count();
    }

    @Override
    @Transactional
    public void insert(Author author) {
        authorDao.insert(author);
    }

    @Override
    public Optional<Author> getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
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
