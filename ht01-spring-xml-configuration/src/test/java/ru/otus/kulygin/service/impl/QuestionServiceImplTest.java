package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.QuestionDao;
import ru.otus.kulygin.service.QuestionService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName(value = "QuestionServiceImpl should ")
class QuestionServiceImplTest {

    private static QuestionService questionService;
    private static QuestionDao questionDao;

    @BeforeAll
    public static void init() {
        questionDao = mock(QuestionDao.class);
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    @DisplayName(value = "find all questions")
    void shouldFindAll() {
        questionService.findAll();

        verify(questionDao).findAll();
    }
}