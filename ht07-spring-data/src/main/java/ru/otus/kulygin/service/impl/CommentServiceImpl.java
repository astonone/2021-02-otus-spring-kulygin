package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    public CommentServiceImpl(CommentRepository commentRepository, MappingService mappingService) {
        this.commentRepository = commentRepository;
        this.mappingService = mappingService;
    }

    @Override
    public Optional<CommentDto> getById(long id) {
        return commentRepository.findById(id).map(comment -> mappingService.map(comment, CommentDto.class));
    }

    @Override
    public void insert(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(long id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findAllByBookId(Long bookId) {
        return mappingService.mapAsList(commentRepository.findAllByBook_Id(bookId), CommentDto.class);
    }

}
