package ru.otus.kulygin.service.impl;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.changelog.AbstractMongoDataLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class MongoDataLoaderService extends AbstractMongoDataLoader {

    public MongoDataLoaderService(ReactiveMongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @PreDestroy
    private void destroy() {
        cleanData();
    }

    @PostConstruct
    private void init() {
        loadData();
    }

}
