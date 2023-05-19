package ru.otus.kulygin.interviewservice.repository.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.kulygin.interviewservice.domain.Interview;
import ru.otus.kulygin.interviewservice.repository.InterviewRepositoryCustom;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class InterviewRepositoryCustomImpl implements InterviewRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public InterviewRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean existByCriteriaId(String criteriaId) {
        Query query = new Query();
        query.addCriteria(where("interviewTemplate.criterias").elemMatch(where("_id").is(criteriaId)));

        return mongoTemplate.exists(query, Interview.class);
    }

}
