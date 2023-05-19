package ru.otus.kulygin.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.userservice.domain.Interviewer;

public interface InterviewerRepository extends MongoRepository<Interviewer, String> {
    @Override
    Page<Interviewer> findAll(Pageable pageable);

    Interviewer findByUsername(String username);

    boolean existsByUsername(String username);

}
