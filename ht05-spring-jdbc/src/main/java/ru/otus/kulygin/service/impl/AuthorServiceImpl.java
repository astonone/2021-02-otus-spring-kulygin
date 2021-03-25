package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.EntityUniqIdException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public int count() {
        return authorDao.count();
    }

    @Override
    public void insert(Author author) {
        try {
            authorDao.insert(author);
        } catch (Exception e) {
            throw new EntityUniqIdException("Entity with this id already exists: ", e);
        }
    }

    @Override
    public Author getById(long id) {
        try {
            return authorDao.getById(id);
        } catch (Exception e) {
            throw new AuthorDoesNotExistException("Author does not exist: ", e);
        }
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
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
