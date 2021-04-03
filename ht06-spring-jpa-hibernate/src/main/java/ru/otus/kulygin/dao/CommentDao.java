package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    boolean existsById(long id);

    Optional<Comment> getById(long id);

    void insert(Comment comment);

    void deleteById(long id);

    List<Comment> findAllByBookId(Long bookId);

}
