package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.QuestionDao;
import ru.otus.kulygin.exception.QuestionsLoadingException;
import ru.otus.kulygin.service.QuestionService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName(value = "not find all questions")
    void shouldNotFindAll() {
        when(questionDao.findAll()).thenThrow(new QuestionsLoadingException("lalalal!", new IOException()));
        Throwable throwable = assertThrows(QuestionsLoadingException.class, () -> questionService.findAll());

        assertThat(throwable.getMessage()).isEqualTo("lalalal!");
    }
}