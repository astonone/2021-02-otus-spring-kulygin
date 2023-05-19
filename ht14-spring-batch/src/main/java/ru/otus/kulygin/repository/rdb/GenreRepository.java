package ru.otus.kulygin.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.rdb.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
