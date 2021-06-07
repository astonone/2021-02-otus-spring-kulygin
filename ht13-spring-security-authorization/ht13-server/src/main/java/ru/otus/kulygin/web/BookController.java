package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.list.BookListDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.service.BookService;

import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.BOOK_NOT_FOUND;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new BookListDto(bookService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/api/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id) {
        val bookById = bookService.getById(id);
        if (bookById.isEmpty()) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookById.get(), HttpStatus.OK);
    }

    @PostMapping("/api/book")
    public ResponseEntity<?> save(@RequestBody BookDto bookDto) {
        try {
            return new ResponseEntity<>(bookService.save(bookDto), HttpStatus.OK);
        } catch (BookDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            bookService.deleteById(id);
        } catch (BookDoesNotExistException e) {
            return new ResponseEntity<>(new ErrorDto(BOOK_NOT_FOUND.getCode(), BOOK_NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
