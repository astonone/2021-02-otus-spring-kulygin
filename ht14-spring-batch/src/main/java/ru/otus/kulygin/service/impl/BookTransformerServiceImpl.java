package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.mongodb.BookDocument;
import ru.otus.kulygin.domain.rdb.Book;
import ru.otus.kulygin.repository.mongodb.AuthorDocumentRepository;
import ru.otus.kulygin.repository.mongodb.BookDocumentRepository;
import ru.otus.kulygin.repository.mongodb.GenreDocumentRepository;
import ru.otus.kulygin.service.BookTransformerService;

@Service
public class BookTransformerServiceImpl implements BookTransformerService {

    private final GenreDocumentRepository genreDocumentRepository;
    private final AuthorDocumentRepository authorDocumentRepository;
    private final BookDocumentRepository bookDocumentRepository;

    public BookTransformerServiceImpl(GenreDocumentRepository genreDocumentRepository, AuthorDocumentRepository authorDocumentRepository, BookDocumentRepository bookDocumentRepository) {
        this.genreDocumentRepository = genreDocumentRepository;
        this.authorDocumentRepository = authorDocumentRepository;
        this.bookDocumentRepository = bookDocumentRepository;
    }

    @Override
    public BookDocument transform(Book book) {
        if (bookDocumentRepository.existsByTitleAndAuthor_FirstNameAndAuthor_LastName(book.getTitle(),
                book.getAuthor().getFirstName(), book.getAuthor().getLastName())) {
            return null;
        }
        return BookDocument.builder()
                .title(book.getTitle())
                .genre(genreDocumentRepository.findByName(book.getGenre().getName()))
                .author(authorDocumentRepository.findByFirstNameAndLastName(
                        book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .build();
    }

}
