package ru.otus.kulygin.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.rdb.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
