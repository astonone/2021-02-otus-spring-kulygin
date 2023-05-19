package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();

}
