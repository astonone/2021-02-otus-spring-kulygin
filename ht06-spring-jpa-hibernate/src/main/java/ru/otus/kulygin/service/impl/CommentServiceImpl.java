package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final MappingService mappingService;

    public CommentServiceImpl(CommentDao commentDao, MappingService mappingService) {
        this.commentDao = commentDao;
        this.mappingService = mappingService;
    }

    @Override
    public Optional<CommentDto> getById(long id) {
        return commentDao.getById(id).map(comment -> mappingService.map(comment, CommentDto.class));
    }

    @Override
    public void insert(Comment comment) {
        commentDao.insert(comment);
    }

    @Override
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }

    @Override
    public List<CommentDto> findAllByBookId(Long bookId) {
        return mappingService.mapAsList(commentDao.findAllByBookId(bookId), CommentDto.class);
    }

}
