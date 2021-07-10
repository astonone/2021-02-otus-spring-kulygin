package ru.otus.kulygin.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.service.CommentService;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.BOOK_NOT_FOUND;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.COMMENT_NOT_FOUND;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/book/{bookId}/comment")
    public ResponseEntity<?> addComment(@PathVariable String bookId, @RequestBody CommentDto comment) {
        try {
            return new ResponseEntity<>(commentService.addCommentToBook(comment.getCommentatorName(), comment.getText(), bookId), HttpStatus.OK);
        } catch (BookDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/book/{bookId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String bookId, @PathVariable String commentId) {
        try {
            return new ResponseEntity<>(commentService.removeCommentFromBook(commentId, bookId), HttpStatus.OK);
        } catch (BookDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CommentDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(COMMENT_NOT_FOUND.getCode(), COMMENT_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
