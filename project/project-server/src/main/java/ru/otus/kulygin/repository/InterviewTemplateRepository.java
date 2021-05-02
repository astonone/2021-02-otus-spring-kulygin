package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.kulygin.domain.InterviewTemplate;

import java.util.List;

public interface InterviewTemplateRepository extends MongoRepository<InterviewTemplate, String>, InterviewTemplateRepositoryCustom {

    @Query("{criterias: {$elemMatch: {_id:?0}}}")
    List<InterviewTemplate> findAllByCriteriaId(String criteriaId);

}
