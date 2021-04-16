package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {

    Book existsByGenre_Id(String genreId);

    Book existsByAuthor_Id(String authorId);

}
