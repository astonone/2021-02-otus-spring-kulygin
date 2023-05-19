package ru.otus.kulygin.templateservice.repository.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.kulygin.templateservice.domain.InterviewTemplate;
import ru.otus.kulygin.templateservice.repository.InterviewTemplateRepositoryCustom;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class InterviewTemplateRepositoryCustomImpl implements InterviewTemplateRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public InterviewTemplateRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean existByCriteriaId(String criteriaId) {
        Query query = new Query();
        query.addCriteria(where("criterias").elemMatch(where("_id").is(criteriaId)));

        return mongoTemplate.exists(query, InterviewTemplate.class);
    }

}
