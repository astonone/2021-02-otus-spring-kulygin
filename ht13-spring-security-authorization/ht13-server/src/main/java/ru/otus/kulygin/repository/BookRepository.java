package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByGenre_Id(String genreId);

    boolean existsByAuthor_Id(String authorId);

    List<Book> findAllByGenre_Id(String genreId);

    List<Book> findAllByAuthor_Id(String authorId);

}
