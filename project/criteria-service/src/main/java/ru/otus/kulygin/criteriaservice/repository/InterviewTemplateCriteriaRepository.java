package ru.otus.kulygin.criteriaservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;

public interface InterviewTemplateCriteriaRepository extends MongoRepository<InterviewTemplateCriteria, String> {

    boolean existsByNameAndPositionType(String name, String positionType);

}
