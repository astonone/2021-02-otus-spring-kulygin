package ru.otus.kulygin.shell;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.domain.Options;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.provider.LocaleProvider;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.UiService;

import java.sql.SQLException;

@ShellComponent
public class ApplicationCommands {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final LocaleProvider localeProvider;
    private final UiService uiFacade;

    public ApplicationCommands(AuthorService authorService, GenreService genreService, BookService bookService,
                               LocaleProvider localeProvider, UiService uiFacade) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.uiFacade = uiFacade;
        this.localeProvider = localeProvider;
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

    @ShellMethod(value = "Get genre by id(Integer id)", key = {"gg", "genre-get"})
    public String getGenreById(@ShellOption Integer id) {
        try {
            return uiFacade.getLocalizedMessageForUser("genre.found",
                    uiFacade.getObjectForPrettyPrint(genreService.getById(id)));
        } catch (GenreDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("genre.not-found", String.valueOf(id));
        }
    }

    @ShellMethod(value = "Delete genre by id(Integer id)", key = {"gd", "genre-delete"})
    public String deleteGenreById(@ShellOption Integer id) {
        try {
            genreService.deleteById(id);
            return uiFacade.getLocalizedMessageForUser("genre.delete", String.valueOf(id));
        } catch (GenreDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("genre.not-found", String.valueOf(id));
        } catch (RelatedEntityException e) {
            return uiFacade.getLocalizedMessageForUser("genre.related-entity");
        }
    }

    @ShellMethod(value = "Insert new genre(Integer id, String name)", key = {"gi", "genre-insert"})
    public String insertGenre(@ShellOption Integer id, @ShellOption String name) {
        try {
            final Genre genreForSave = Genre.builder()
                    .id(id)
                    .name(name)
                    .build();
            genreService.insert(genreForSave);
            return uiFacade.getLocalizedMessageForUser("genre.insert",
                    uiFacade.getObjectForPrettyPrint(genreForSave));
        } catch (Exception e) {
            return uiFacade.getLocalizedMessageForUser("genre.not-insert");
        }
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

    @ShellMethod(value = "Get author by id(Integer id)", key = {"ag", "author-get"})
    public String getAuthorById(@ShellOption Integer id) {
        try {
            return uiFacade.getLocalizedMessageForUser("author.found",
                    uiFacade.getObjectForPrettyPrint(authorService.getById(id)));
        } catch (GenreDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("author.not-found", String.valueOf(id));
        }
    }

    @ShellMethod(value = "Delete author by id(Integer id)", key = {"ad", "author-delete"})
    public String deleteAuthorById(@ShellOption Integer id) {
        try {
            authorService.deleteById(id);
            return uiFacade.getLocalizedMessageForUser("author.delete", String.valueOf(id));
        } catch (AuthorDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("author.not-found", String.valueOf(id));
        } catch (RelatedEntityException e) {
            return uiFacade.getLocalizedMessageForUser("author.related-entity");
        }
    }

    @ShellMethod(value = "Insert new author(Integer id, String fname, String lname)", key = {"ai", "author-insert"})
    public String insertAuthor(@ShellOption Integer id, @ShellOption String fname, @ShellOption String lname) {
        try {
            final Author authorForSave = Author.builder()
                    .id(id)
                    .firstName(fname)
                    .lastName(lname)
                    .build();
            authorService.insert(authorForSave);
            return uiFacade.getLocalizedMessageForUser("author.insert",
                    uiFacade.getObjectForPrettyPrint(authorForSave));
        } catch (Exception e) {
            return uiFacade.getLocalizedMessageForUser("author.not-insert");
        }
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

    @ShellMethod(value = "Get book by id(Integer id)", key = {"bg", "book-get"})
    public String getBookById(@ShellOption Integer id) {
        try {
            return uiFacade.getLocalizedMessageForUser("book.found",
                    uiFacade.getObjectForPrettyPrint(bookService.getById(id)));
        } catch (GenreDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("book.not-found", String.valueOf(id));
        }
    }

    @ShellMethod(value = "Get book by id partial(Integer id)", key = {"bgl", "book-get-partial"})
    public String getBookByIdPartial(@ShellOption Integer id) {
        try {
            return uiFacade.getLocalizedMessageForUser("book.found",
                    uiFacade.getObjectForPrettyPrint(bookService.getById(id, Options.builder()
                            .isPartialLoading(true)
                            .build())));
        } catch (GenreDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("book.not-found", String.valueOf(id));
        }
    }

    @ShellMethod(value = "Delete book by id(Integer id)", key = {"bd", "book-delete"})
    public String deleteBookById(@ShellOption Integer id) {
        try {
            bookService.deleteById(id);
            return uiFacade.getLocalizedMessageForUser("book.delete", String.valueOf(id));
        } catch (BookDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("book.not-found", String.valueOf(id));
        }
    }

    @ShellMethod(value = "Insert new book(Integer id, String title, Integer authorId, Integer genreId)", key = {"bi", "book-insert"})
    public String insertBook(@ShellOption Integer id, @ShellOption String title, @ShellOption Integer authorId, @ShellOption Integer genreId) {
        Author author = null;
        Genre genre = null;
        try {
            author = authorService.getById(authorId);
        } catch (AuthorDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("author.not-found", String.valueOf(authorId));
        }
        try {
            genre = genreService.getById(genreId);
        } catch (GenreDoesNotExistException e) {
            return uiFacade.getLocalizedMessageForUser("genre.not-found", String.valueOf(genreId));
        }
        try {
            final Book bookForSave = Book.builder()
                    .id(id)
                    .title(title)
                    .genre(genre)
                    .author(author)
                    .build();
            bookService.insert(bookForSave);
            return uiFacade.getLocalizedMessageForUser("book.insert",
                    uiFacade.getObjectForPrettyPrint(bookForSave));
        } catch (Exception e) {
            return uiFacade.getLocalizedMessageForUser("book.not-insert");
        }
    }

    @ShellMethod(value = "Get book list", key = {"bga", "book-get-all"})
    public String getBooksList() {
        return uiFacade.getLocalizedMessageForUser("book.get-all",
                uiFacade.getObjectForPrettyPrint(bookService.getAll()));
    }

    @ShellMethod(value = "Get book list partial", key = {"bgal", "book-get-all-partial"})
    public String getBooksListPartial() {
        return uiFacade.getLocalizedMessageForUser("book.get-all",
                uiFacade.getObjectForPrettyPrint(bookService.getAll(Options.builder()
                        .isPartialLoading(true)
                        .build())));
    }

}
