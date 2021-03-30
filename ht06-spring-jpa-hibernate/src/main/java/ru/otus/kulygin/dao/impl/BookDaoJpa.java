package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.exception.BookDoesNotExistException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean existsById(long id) {
        val longTypedQuery = em.createQuery("select count(b) from Book b where b.id = :id", Long.class);
        longTypedQuery.setParameter("id", id);
        val count = longTypedQuery.getSingleResult();

        return count > 0;
    }

    @Override
    public long count() {
        val longTypedQuery = em.createQuery("select count(b) from Book b", Long.class);

        return longTypedQuery.getSingleResult();
    }

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        val entityGraph = em.getEntityGraph("genre-author-entity-graph");

        val bookTypedQuery = em.createQuery("select b from Book b", Book.class);

        bookTypedQuery.setHint("javax.persistence.fetchgraph", entityGraph);

        return bookTypedQuery.getResultList();
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) {
            throw new BookDoesNotExistException("Book does not exist");
        }
        val query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
