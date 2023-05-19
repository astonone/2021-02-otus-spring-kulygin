package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> findAll();

}
