package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.mongodb.AuthorDocument;
import ru.otus.kulygin.domain.rdb.Author;
import ru.otus.kulygin.repository.mongodb.AuthorDocumentRepository;
import ru.otus.kulygin.service.AuthorTransformerService;

@Service
public class AuthorTransformerServiceImpl implements AuthorTransformerService {

    private final AuthorDocumentRepository authorDocumentRepository;

    public AuthorTransformerServiceImpl(AuthorDocumentRepository authorDocumentRepository) {
        this.authorDocumentRepository = authorDocumentRepository;
    }

    @Override
    public AuthorDocument transform(Author author) {
        if (authorDocumentRepository.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName())) {
            return null;
        }
        return AuthorDocument.builder()
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

}
