package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.mongodb.BookDocument;
import ru.otus.kulygin.domain.rdb.Book;

public interface BookTransformerService {

    BookDocument transform(Book book);

}
