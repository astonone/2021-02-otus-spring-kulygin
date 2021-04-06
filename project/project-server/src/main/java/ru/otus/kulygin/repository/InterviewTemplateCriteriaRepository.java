package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;

public interface InterviewTemplateCriteriaRepository extends MongoRepository<InterviewTemplateCriteria, String> {
}
