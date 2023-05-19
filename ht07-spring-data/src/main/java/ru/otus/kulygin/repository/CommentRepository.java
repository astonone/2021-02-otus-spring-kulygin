package ru.otus.kulygin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.kulygin.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBook_Id(Long bookId);

}
