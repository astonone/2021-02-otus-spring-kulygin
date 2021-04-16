package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> getById(String id);

    void insert(Comment comment);

    void deleteById(String id);

    List<CommentDto> findAllByBookId(String bookId);

}
