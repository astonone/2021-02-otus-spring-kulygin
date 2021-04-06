package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.Interviewer;

public interface InterviewerRepository extends MongoRepository<Interviewer, String> {
}
