package ru.otus.kulygin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
