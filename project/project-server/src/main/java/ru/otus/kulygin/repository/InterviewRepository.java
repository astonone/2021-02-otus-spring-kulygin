package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.Interview;

public interface InterviewRepository extends MongoRepository<Interview, String> {
}
