package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.exception.CommentDoesNotExistException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean existsById(long id) {
        val longTypedQuery =
                em.createQuery("select count(c) from Comment c where c.id = :id", Long.class);
        longTypedQuery.setParameter("id", id);
        val count = longTypedQuery.getSingleResult();

        return count > 0;
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void insert(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        val query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        val commentTypedQuery =
                em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        commentTypedQuery.setParameter("bookId", bookId);

        return commentTypedQuery.getResultList();
    }

}
