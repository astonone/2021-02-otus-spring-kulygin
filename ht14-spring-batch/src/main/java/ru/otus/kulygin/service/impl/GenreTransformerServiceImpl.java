package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.mongodb.GenreDocument;
import ru.otus.kulygin.domain.rdb.Genre;
import ru.otus.kulygin.repository.mongodb.GenreDocumentRepository;
import ru.otus.kulygin.service.GenreTransformerService;

@Service
public class GenreTransformerServiceImpl implements GenreTransformerService {

    private final GenreDocumentRepository genreDocumentRepository;

    public GenreTransformerServiceImpl(GenreDocumentRepository genreDocumentRepository) {
        this.genreDocumentRepository = genreDocumentRepository;
    }

    @Override
    public GenreDocument transform(Genre genre) {
        if (genreDocumentRepository.existsByName(genre.getName())) {
            return null;
        }
        return GenreDocument.builder()
                .name(genre.getName())
                .build();
    }

}
