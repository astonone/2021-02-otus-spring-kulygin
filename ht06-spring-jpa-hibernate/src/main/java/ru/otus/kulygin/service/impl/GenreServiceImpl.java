package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public long count() {
        return genreDao.count();
    }

    @Override
    @Transactional
    public void insert(Genre genre) {
        genreDao.insert(genre);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    @Transactional
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
