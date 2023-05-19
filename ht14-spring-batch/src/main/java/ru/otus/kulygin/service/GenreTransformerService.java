package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.mongodb.GenreDocument;
import ru.otus.kulygin.domain.rdb.Genre;

public interface GenreTransformerService {

    GenreDocument transform(Genre genre);

}
