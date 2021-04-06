package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
}
