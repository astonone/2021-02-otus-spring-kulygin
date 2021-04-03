package ru.otus.kulygin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
