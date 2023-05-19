package ru.otus.kulygin.shell;

import lombok.val;
import org.h2.tools.Console;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.*;
import ru.otus.kulygin.provider.LocaleProvider;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.UiService;
import ru.otus.kulygin.service.impl.MappingService;

import java.sql.SQLException;

@ShellComponent
public class ApplicationCommands {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final LocaleProvider localeProvider;
    private final UiService uiFacade;
    private final MappingService mappingService;

    public ApplicationCommands(AuthorService authorService, GenreService genreService, BookService bookService,
                               LocaleProvider localeProvider, UiService uiFacade, MappingService mappingService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.uiFacade = uiFacade;
        this.localeProvider = localeProvider;
        this.mappingService = mappingService;
    }

    @ShellMethod(value = "Open web console", key = {"owc", "open-web-console"})
    public String openWebConsole() throws SQLException {
        Console.main();
        return uiFacade.getLocalizedMessageForUser("web-console.open");
    }

    @ShellMethod(value = "Change app locale(String locale)", key = {"cal", "change-app-locale"})
    public String changeAppLocale(@ShellOption String locale) {
        localeProvider.setLocale(locale);
        return uiFacade.getLocalizedMessageForUser("locale.change");
    }

    @ShellMethod(value = "Count of genres", key = {"gc", "genre-count"})
    public String countGenres() {
        return uiFacade.getLocalizedMessageForUser("genre.count", String.valueOf(genreService.count()));
    }

    @ShellMethod(value = "Get genre by id(Long id)", key = {"gg", "genre-get"})
    public String getGenreById(@ShellOption Long id) {
        val genre = genreService.getById(id);
        return genre.isPresent()
                ? uiFacade.getLocalizedMessageForUser("genre.found",
                uiFacade.getObjectForPrettyPrint(genre.get()))
                : uiFacade.getLocalizedMessageForUser("genre.not-found", String.valueOf(id));
    }

    @ShellMethod(value = "Delete genre by id(Long id)", key = {"gd", "genre-delete"})
    public String deleteGenreById(@ShellOption Long id) {
        try {
            genreService.deleteById(id);
            return uiFacade.getLocalizedMessageForUser("genre.delete", String.valueOf(id));
        } catch (GenreDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("genre.not-found", String.valueOf(id));
        } catch (Exception e) {
            return uiFacade.getLocalizedMessageForUser("genre.related-entity");
        }
    }

    @ShellMethod(value = "Insert new genre(String name)", key = {"gi", "genre-insert"})
    public String insertGenre(@ShellOption String name) {
        val genreForSave = Genre.builder()
                .name(name)
                .build();
        genreService.insert(genreForSave);
        return uiFacade.getLocalizedMessageForUser("genre.insert",
                uiFacade.getObjectForPrettyPrint(genreForSave));
    }

    @ShellMethod(value = "Get genre list", key = {"gga", "genre-get-all"})
    public String getGenresList() {
        return uiFacade.getLocalizedMessageForUser("genre.get-all",
                uiFacade.getObjectForPrettyPrint(genreService.getAll()));
    }

    @ShellMethod(value = "Count of authors", key = {"ac", "author-count"})
    public String countAuthor() {
        return uiFacade.getLocalizedMessageForUser("author.count", String.valueOf(authorService.count()));
    }

    @ShellMethod(value = "Get author by id(Long id)", key = {"ag", "author-get"})
    public String getAuthorById(@ShellOption Integer id) {
        val author = authorService.getById(id);
        return author.isPresent()
                ? uiFacade.getLocalizedMessageForUser("author.found",
                uiFacade.getObjectForPrettyPrint(author.get()))
                : uiFacade.getLocalizedMessageForUser("author.not-found", String.valueOf(id));
    }

    @ShellMethod(value = "Delete author by id(Long id)", key = {"ad", "author-delete"})
    public String deleteAuthorById(@ShellOption Long id) {
        try {
            authorService.deleteById(id);
            return uiFacade.getLocalizedMessageForUser("author.delete", String.valueOf(id));
        } catch (AuthorDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("author.not-found", String.valueOf(id));
        } catch (Exception e) {
            return uiFacade.getLocalizedMessageForUser("author.related-entity");
        }
    }

    @ShellMethod(value = "Insert new author(String fname, String lname)", key = {"ai", "author-insert"})
    public String insertAuthor(@ShellOption String fname, @ShellOption String lname) {
        val authorForSave = Author.builder()
                .firstName(fname)
                .lastName(lname)
                .build();
        authorService.insert(authorForSave);
        return uiFacade.getLocalizedMessageForUser("author.insert",
                uiFacade.getObjectForPrettyPrint(authorForSave));
    }

    @ShellMethod(value = "Get author list", key = {"aga", "author-get-all"})
    public String getAuthorsList() {
        return uiFacade.getLocalizedMessageForUser("author.get-all",
                uiFacade.getObjectForPrettyPrint(authorService.getAll()));
    }

    @ShellMethod(value = "Count of books", key = {"bc", "book-count"})
    public String countBooks() {
        return uiFacade.getLocalizedMessageForUser("book.count", String.valueOf(bookService.count()));
    }

    @ShellMethod(value = "Get book by id(Long id)", key = {"bg", "book-get"})
    public String getBookById(@ShellOption Long id) {
        val book = bookService.getById(id);
        return book.isPresent()
                ? uiFacade.getLocalizedMessageForUser("book.found",
                uiFacade.getObjectForPrettyPrint(book.get()))
                : uiFacade.getLocalizedMessageForUser("book.not-found", String.valueOf(id));
    }

    @ShellMethod(value = "Delete book by id(Integer id)", key = {"bd", "book-delete"})
    public String deleteBookById(@ShellOption Long id) {
        try {
            bookService.deleteById(id);
            return uiFacade.getLocalizedMessageForUser("book.delete", String.valueOf(id));
        } catch (BookDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("book.not-found", String.valueOf(id));
        }
    }

    @ShellMethod(value = "Insert new book(String title, Integer authorId, Long genreId)", key = {"bi", "book-insert"})
    public String insertBook(@ShellOption String title, @ShellOption Long authorId, @ShellOption Long genreId) {
        val author = authorService.getById(authorId);
        val genre = genreService.getById(genreId);
        if (author.isEmpty()) {
            return uiFacade.getLocalizedMessageForUser("author.not-found", String.valueOf(authorId));
        }
        if (genre.isEmpty()) {
            return uiFacade.getLocalizedMessageForUser("genre.not-found", String.valueOf(genreId));
        }
        final Book bookForSave = Book.builder()
                .title(title)
                .genre(mappingService.map(genre.get(), Genre.class))
                .author(mappingService.map(author.get(), Author.class))
                .build();
        bookService.insert(bookForSave);
        return uiFacade.getLocalizedMessageForUser("book.insert",
                uiFacade.getObjectForPrettyPrint(bookForSave));
    }

    @ShellMethod(value = "Get book list", key = {"bga", "book-get-all"})
    public String getBooksList() {
        return uiFacade.getLocalizedMessageForUser("book.get-all",
                uiFacade.getObjectForPrettyPrint(bookService.getAll()));
    }

    @ShellMethod(value = "Add comment to book(String commentatorName, String text, Long bookId)", key = {"ca", "comment-add"})
    public String addCommentToBook(@ShellOption String commentatorName, @ShellOption String text, @ShellOption Long bookId) {
        val book = bookService.getById(bookId);
        if (book.isEmpty()) {
            return uiFacade.getLocalizedMessageForUser("book.not-found", String.valueOf(bookId));
        }
        val updatedBook = bookService.addCommentToBook(commentatorName, text, mappingService.map(book.get(), Book.class));
        return uiFacade.getLocalizedMessageForUser("comment.insert",
                uiFacade.getObjectForPrettyPrint(updatedBook));
    }

    @ShellMethod(value = "Add comment to book(Long commentId)", key = {"cd", "comment-delete"})
    public String removeCommentFromBook(@ShellOption Long commentId) {
        try {
            val book = bookService.removeCommentFromBook(commentId);
            return uiFacade.getLocalizedMessageForUser("comment.delete",
                    uiFacade.getObjectForPrettyPrint(book));
        } catch (CommentDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("comment.not-found", String.valueOf(commentId));
        }
    }

}
