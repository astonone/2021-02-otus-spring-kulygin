package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.mongodb.AuthorDocument;
import ru.otus.kulygin.domain.rdb.Author;

public interface AuthorTransformerService {

    AuthorDocument transform(Author author);

}
