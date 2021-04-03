package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean existsById(long id) {
        val longTypedQuery =
                em.createQuery("select count(a) from Author a where a.id = :id", Long.class);
        longTypedQuery.setParameter("id", id);
        val count = longTypedQuery.getSingleResult();

        return count > 0;
    }

    @Override
    public long count() {
        val longTypedQuery = em.createQuery("select count(a) from Author a", Long.class);

        return longTypedQuery.getSingleResult();
    }

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getAll() {
        val authorTypedQuery = em.createQuery("select a from Author a", Author.class);

        return authorTypedQuery.getResultList();
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) {
            throw new AuthorDoesNotExistException("Author does not exist");
        }
        val query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
