package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.impl.TestResultDaoImpl;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestResultService;
import ru.otus.kulygin.service.TestingService;
import ru.otus.kulygin.service.UiService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName(value = "TestingServiceImpl should ")
class TestingServiceImplTest {

    private static TestingService testingService;
    private static UiService uiService;
    private static QuestionService questionService;

    @BeforeAll
    public static void init() {
        uiService = mock(UiService.class);
        questionService = mock(QuestionService.class);
        TestResultService testResultService = new TestResultServiceImpl(new TestResultDaoImpl());
        testingService = new TestingServiceImpl(questionService, uiService, testResultService);
    }

    @Test
    @DisplayName(value = "start testing process")
    void shouldDoTest() {
        final Student student = new Student("Ivan", "Ivanov");
        List<Question> questionsAndAnswers = new ArrayList<>();
        questionsAndAnswers.add(new Question("How much 2+2?", "4"));
        questionsAndAnswers.add(new Question("How much 2*2?", "4"));
        questionsAndAnswers.add(new Question("How much 2/2?", "1"));
        questionsAndAnswers.add(new Question("How much 2+3?", "5"));
        questionsAndAnswers.add(new Question("How much 2+5?", "7"));
        when(questionService.findAll()).thenReturn(questionsAndAnswers);
        when(uiService.in())
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "2")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "7");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(4);
    }
}