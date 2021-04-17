package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.exception.IdNullException;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.CommentService;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final CommentService commentService;
    private final MappingService mappingService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService, CommentService commentService, MappingService mappingService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.commentService = commentService;
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
                .map(bookDto -> mappingService.map(bookDto, Book.class))
                .orElseThrow(() -> new BookDoesNotExistException("Book with id=" + id + " has not found")));
        return "book-edit";
    }

    @PostMapping("/edit-book")
    public String editBook(Book book, Model model) {
        val genre = genreService.getById(book.getGenre().getId());
        book.setGenre(genre.map(genreDto -> mappingService
                .map(genreDto, Genre.class))
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id=" + book.getGenre().getId() + " has not found")));
        val author = authorService.getById(book.getAuthor().getId());
        book.setAuthor(author.map(authorDto -> mappingService
                .map(authorDto, Author.class))
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id=" + book.getAuthor().getId() + " has not found")));
        bookService.save(book);
        model.addAttribute("book", book);
        return "redirect:/";
    }

    @GetMapping("/create-book")
    public String createBookPage(Model model) {
        val author = new Author();
        val genre = new Genre();
        val book = new Book();
        book.setGenre(genre);
        book.setAuthor(author);
        model.addAttribute("book", book);
        return "book-create";
    }

    @PostMapping("/create-book")
    public String createBook(Book book, Model model) {
        if (book.getGenre().getId().isEmpty()) {
            throw new IdNullException("Id for genre must not be null");
        }
        val genre = genreService.getById(book.getGenre().getId());
        book.setGenre(genre.map(genreDto -> mappingService
                .map(genreDto, Genre.class))
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id=" + book.getGenre().getId() + " has not found")));
        if (book.getAuthor().getId().isEmpty()) {
            throw new IdNullException("Id for author must not be null");
        }
        val author = authorService.getById(book.getAuthor().getId());
        book.setAuthor(author.map(authorDto -> mappingService
                .map(authorDto, Author.class))
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id=" + book.getAuthor().getId() + " has not found")));
        bookService.save(book);
        model.addAttribute("book", book);
        return "redirect:/";
    }

    @PostMapping("/delete-book")
    public String deleteBook(@RequestParam("id") String id, Model model) {
        bookService.deleteById(id);
        val books = bookService.getAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/book-comments")
    public String commentsBookPage(@RequestParam("id") String id, Model model) {
        val comments = commentService.findAllByBookId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", id);
        return "book-comments";
    }

    @PostMapping("/delete-comment")
    public String deleteComment(@RequestParam("commentId") String commentId,
                                @RequestParam("bookId") String bookId, Model model) {
        bookService.removeCommentFromBook(commentId);
        List<CommentDto> comments = commentService.findAllByBookId(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "book-comments";
    }

    @GetMapping("/book-comment-create")
    public String createComment(@RequestParam("id") String bookId, Model model) {
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        model.addAttribute("bookId", bookId);
        return "book-comment-create";
    }

    @PostMapping("/book-comment-create")
    public String createComment(@RequestParam("bookId") String bookId, Comment comment, Model model) {
        val book = bookService.getById(bookId);
        bookService.addCommentToBook(comment.getCommentatorName(), comment.getText(), book
                .map(bookDto -> mappingService.map(bookDto, Book.class))
                .orElseThrow(() -> new BookDoesNotExistException("Book with id=" + bookId + " has not found")));
        val comments = commentService.findAllByBookId(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "book-comments";
    }

}
