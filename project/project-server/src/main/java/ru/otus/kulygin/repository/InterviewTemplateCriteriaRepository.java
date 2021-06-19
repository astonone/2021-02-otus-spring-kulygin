package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;

import java.util.List;

public interface InterviewTemplateCriteriaRepository extends MongoRepository<InterviewTemplateCriteria, String> {

    List<InterviewTemplateCriteria> findAllByPositionType(String positionType);

    boolean existsByNameAndPositionType(String name, String positionType);

}
