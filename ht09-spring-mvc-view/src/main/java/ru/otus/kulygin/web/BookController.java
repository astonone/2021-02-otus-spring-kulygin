package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.exception.IdNullException;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

@Controller
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

    @GetMapping("/")
    public String listBooks(Model model) {
        val books = bookService.getAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/edit-book")
    public String editBookPage(@RequestParam("id") String id, Model model) {
        val book = bookService.getById(id);
        model.addAttribute("book", book
                .orElseThrow(() -> new BookDoesNotExistException("Book with id=" + id + " has not found")));
        val genres = genreService.getAll();
        val authors = authorService.getAll();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "book-edit";
    }

    @PostMapping("/edit-book")
    public String editBook(BookDto book) {
        val genre = genreService.getById(book.getGenre().getId());
        book.setGenre(genre
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id=" + book.getGenre().getId() + " has not found")));
        val author = authorService.getById(book.getAuthor().getId());
        book.setAuthor(author
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id=" + book.getAuthor().getId() + " has not found")));
        bookService.save(mappingService.map(book, Book.class));
        return "redirect:/";
    }

    @GetMapping("/create-book")
    public String createBookPage(Model model) {
        val author = new AuthorDto();
        val genre = new GenreDto();
        val book = new BookDto();
        book.setGenre(genre);
        book.setAuthor(author);
        val genres = genreService.getAll();
        val authors = authorService.getAll();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "book-create";
    }

    @PostMapping("/create-book")
    public String createBook(BookDto book) {
        if (book.getGenre().getId().isEmpty()) {
            throw new IdNullException("Id for genre must not be null");
        }
        val genre = genreService.getById(book.getGenre().getId());
        book.setGenre(genre
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id=" + book.getGenre().getId() + " has not found")));
        if (book.getAuthor().getId().isEmpty()) {
            throw new IdNullException("Id for author must not be null");
        }
        val author = authorService.getById(book.getAuthor().getId());
        book.setAuthor(author
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id=" + book.getAuthor().getId() + " has not found")));
        bookService.save(mappingService.map(book, Book.class));
        return "redirect:/";
    }

    @PostMapping("/delete-book")
    public String deleteBook(@RequestParam("id") String id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

}
