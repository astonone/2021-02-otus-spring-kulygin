package ru.otus.kulygin.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.rdb.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
