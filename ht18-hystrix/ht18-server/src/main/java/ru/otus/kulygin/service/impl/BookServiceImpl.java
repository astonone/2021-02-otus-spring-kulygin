package ru.otus.kulygin.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.repository.AuthorRepository;
import ru.otus.kulygin.repository.BookRepository;
import ru.otus.kulygin.repository.GenreRepository;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.otus.kulygin.service.impl.AuthorServiceImpl.N_A;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final MappingService mappingService;

    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository, AuthorRepository authorRepository, MappingService mappingService) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.mappingService = mappingService;
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Secured("ROLE_ADMIN")
    @HystrixCommand(fallbackMethod="buildFallbackCreateBook")
    @Override
    public BookDto save(BookDto bookDto) {
        Book forSave = Book.builder().build();
        Optional<Book> bookById = Optional.empty();
        if (bookDto.getId() != null) {
            bookById = bookRepository.findById(bookDto.getId());
            if (bookById.isEmpty()) {
                throw new BookDoesNotExistException();
            }
        }
        forSave.setId(bookById.map(Book::getId).orElse(null));
        forSave.setTitle(bookDto.getTitle());
        forSave.setGenre(genreRepository.findById(bookDto.getGenre().getId()).orElse(null));
        forSave.setAuthor(authorRepository.findById(bookDto.getAuthor().getId()).orElse(null));
        return mappingService.map(bookRepository.save(forSave), BookDto.class);
    }

    public BookDto buildFallbackCreateBook(BookDto bookDto) {
        return BookDto.builder()
                .id(N_A)
                .build();
    }

    @Override
    @HystrixCommand(fallbackMethod="buildFallbackBook")
    public Optional<BookDto> getById(String id) {
        return bookRepository.findById(id).map(book -> mappingService.map(book, BookDto.class));
    }

    public Optional<BookDto> buildFallbackBook(String id) {
        return Optional.of(BookDto.builder()
                .id(N_A)
                .build());
    }

    @Override
    @HystrixCommand(fallbackMethod="buildFallbackBooks")
    public List<BookDto> getAll() {
        return mappingService.mapAsList(bookRepository.findAll(), BookDto.class);
    }

    public List<BookDto> buildFallbackBooks() {
        return Collections.singletonList(BookDto.builder()
                .id(N_A)
                .build());
    }

    @Secured("ROLE_ADMIN")
    @HystrixCommand(fallbackMethod="buildFallbackDeleteBook")
    @Override
    public void deleteById(String id) {
        if (!bookRepository.existsById(id)) {
            throw new BookDoesNotExistException("Book does not exist");
        }
        bookRepository.deleteById(id);
    }

    public void buildFallbackDeleteBook(String id) {
        // nothing
    }

}
