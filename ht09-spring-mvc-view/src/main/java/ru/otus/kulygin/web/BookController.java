package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.GenreService;

@Controller
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "book-list";
    }

    @GetMapping("/edit-book")
    public String editBookPage(@RequestParam("id") String id, Model model) {
        model.addAttribute("book", bookService.getById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id=" + id + " has not found")));
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "book-edit";
    }

    @PostMapping("/edit-book")
    public String editBook(BookDto book) {
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/create-book")
    public String createBookPage(Model model) {
        val book = new BookDto();
        book.setGenre(new GenreDto());
        book.setAuthor(new AuthorDto());
        val genres = genreService.getAll();
        val authors = authorService.getAll();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "book-create";
    }

    @PostMapping("/create-book")
    public String createBook(BookDto book) {
        bookService.save(book);
        return "redirect:/";
    }

    @PostMapping("/delete-book")
    public String deleteBook(@RequestParam("id") String id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

}
