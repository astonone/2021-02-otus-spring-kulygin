package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.GenreDoesNotExistException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean existsById(long id) {
        val longTypedQuery = em.createQuery("select count(g) from Genre g where g.id = :id", Long.class);
        longTypedQuery.setParameter("id", id);
        val count = longTypedQuery.getSingleResult();

        return count > 0;
    }

    @Override
    public long count() {
        val longTypedQuery = em.createQuery("select count(g) from Genre g", Long.class);

        return longTypedQuery.getSingleResult();
    }

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getAll() {
        val genreTypedQuery = em.createQuery("select g from Genre g", Genre.class);

        return genreTypedQuery.getResultList();
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) {
            throw new GenreDoesNotExistException("Genre does not exist");
        }
        val query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
