package ru.otus.kulygin.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(attributePaths = {"author"})
    List<Book> findAll();

}
