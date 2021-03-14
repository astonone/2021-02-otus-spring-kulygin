package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.dao.QuestionDao;
import ru.otus.kulygin.exception.QuestionsLoadingException;
import ru.otus.kulygin.service.QuestionService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = QuestionServiceImpl.class)
@DisplayName(value = "QuestionServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;

    @MockBean
    private QuestionDao questionDao;

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