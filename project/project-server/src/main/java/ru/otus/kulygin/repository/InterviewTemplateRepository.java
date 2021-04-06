package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.InterviewTemplate;

public interface InterviewTemplateRepository extends MongoRepository<InterviewTemplate, String> {
}
