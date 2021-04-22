package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.list.BookListDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Optional;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.BOOK_NOT_FOUND;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.COMMENT_NOT_FOUND;

@RestController
@RequestMapping(path = "/api/book")
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final MappingService mappingService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService, MappingService mappingService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.mappingService = mappingService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new BookListDto(bookService.getAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id) {
        val byId = bookService.getById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody BookDto bookDto) {
        Book forSave = Book.builder().build();
        Optional<BookDto> bookById = Optional.empty();
        if (bookDto.getId() != null) {
            bookById = bookService.getById(bookDto.getId());
            if (bookById.isEmpty()) {
                return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
            }
        }
        forSave.setId(bookById.map(BookDto::getId).orElse(null));
        forSave.setTitle(bookDto.getTitle());
        forSave.setGenre(genreService.getById(bookDto.getGenre().getId()).map(g -> mappingService.map(g, Genre.class)).orElse(null));
        forSave.setAuthor(authorService.getById(bookDto.getAuthor().getId()).map(a -> mappingService.map(a, Author.class)).orElse(null));
        return new ResponseEntity<>(bookService.save(forSave), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            bookService.deleteById(id);
        } catch (BookDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/comment")
    public ResponseEntity<?> addComment(@PathVariable String id, @RequestBody CommentDto comment) {
        val byId = bookService.getById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.addCommentToBook(comment.getCommentatorName(), comment.getText(),
                mappingService.map(byId.get(), Book.class)), HttpStatus.OK);
    }

    @DeleteMapping("{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String id, @PathVariable String commentId) {
        val byId = bookService.getById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
        BookDto bookDto;
        try {
            bookDto = bookService.removeCommentFromBook(commentId);
        } catch (CommentDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(COMMENT_NOT_FOUND.getCode(), COMMENT_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

}
