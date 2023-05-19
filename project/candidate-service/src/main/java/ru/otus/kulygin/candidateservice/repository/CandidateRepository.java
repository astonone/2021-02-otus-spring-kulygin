package ru.otus.kulygin.candidateservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.candidateservice.domain.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
}
