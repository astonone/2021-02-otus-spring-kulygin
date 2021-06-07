package ru.otus.kulygin.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.rdb.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
