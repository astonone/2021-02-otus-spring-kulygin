package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.EntityUniqIdException;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public int count() {
        return genreDao.count();
    }

    @Override
    public void insert(Genre genre) {
        try {
            genreDao.insert(genre);
        } catch (Exception e) {
            throw new EntityUniqIdException("Entity with this id already exists: ", e);
        }
    }

    @Override
    public Genre getById(long id) {
        try {
            return genreDao.getById(id);
        } catch (Exception e) {
            throw new GenreDoesNotExistException("Genre does not exist: ", e);
        }
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public void deleteById(long id) {
        try {
            genreDao.deleteById(id);
        } catch (GenreDoesNotExistException e) {
            throw new GenreDoesNotExistException(e);
        } catch (Exception e) {
            throw new RelatedEntityException("Seems Genre has related book: ", e);
        }
    }
}
