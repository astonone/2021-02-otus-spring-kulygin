package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.exception.QuestionsLoadingException;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName(value = "TestingServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TestingServiceImplTest {

    @Configuration
    static class NestedConfiguration {
        @Bean
        UiFacade uiLocalizedFacade() {
            return mock(UiFacade.class);
        }

        @Bean
        QuestionService questionService() {
            return mock(QuestionService.class);
        }

        @Bean
        TestingService testingService() {
            return new TestingServiceImpl(questionService(), uiLocalizedFacade(), 3);
        }
    }

    @Autowired
    private TestingService testingService;

    @Autowired
    private UiFacade uiFacade;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName(value = "start testing process and successfully finish it and pass it")
    void shouldDoTestAndPassIt() {
        final Student student = new Student("Ivan", "Ivanov");
        List<Question> questionsAndAnswers = new ArrayList<>();
        questionsAndAnswers.add(new Question("How much 2+2?", "4"));
        questionsAndAnswers.add(new Question("How much 2*2?", "4"));
        questionsAndAnswers.add(new Question("How much 2/2?", "1"));
        questionsAndAnswers.add(new Question("How much 2+3?", "5"));
        questionsAndAnswers.add(new Question("How much 2+5?", "7"));
        when(questionService.findAll()).thenReturn(questionsAndAnswers);
        when(uiFacade.getMessageFromUser())
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "2")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "7");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(4);
        verify(uiFacade).showLocalizedMessageForUser("testing.pass");
    }

    @Test
    @DisplayName(value = "start testing process and successfully finish it and not pass it")
    void shouldDoTestAndNotPassIt() {
        final Student student = new Student("Ivan", "Ivanov");
        List<Question> questionsAndAnswers = new ArrayList<>();
        questionsAndAnswers.add(new Question("How much 2+2?", "4"));
        questionsAndAnswers.add(new Question("How much 2*2?", "4"));
        questionsAndAnswers.add(new Question("How much 2/2?", "1"));
        questionsAndAnswers.add(new Question("How much 2+3?", "5"));
        questionsAndAnswers.add(new Question("How much 2+5?", "7"));
        when(questionService.findAll()).thenReturn(questionsAndAnswers);
        when(uiFacade.getMessageFromUser())
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "0")
                .thenAnswer(a -> "6")
                .thenAnswer(a -> "8");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(2);
        verify(uiFacade).showLocalizedMessageForUser("testing.notpass");
    }

    @Test
    @DisplayName(value = "start testing process with input error")
    void shouldNotDoTest_inputError() {
        final Student student = new Student("Ivan", "Ivanov");
        List<Question> questionsAndAnswers = new ArrayList<>();
        questionsAndAnswers.add(new Question("How much 2+2?", "4"));
        questionsAndAnswers.add(new Question("How much 2*2?", "4"));
        questionsAndAnswers.add(new Question("How much 2/2?", "1"));
        questionsAndAnswers.add(new Question("How much 2+3?", "5"));
        questionsAndAnswers.add(new Question("How much 2+5?", "7"));
        when(uiFacade.getMessageFromUser()).thenThrow(new UserInputException(new IOException("Aaaaaa, console was damaged!")));
        when(questionService.findAll()).thenReturn(questionsAndAnswers);

        Throwable throwable = assertThrows(UserInputException.class, () -> testingService.doTest(student));

        assertThat(throwable.getMessage()).isEqualTo("java.io.IOException: Aaaaaa, console was damaged!");
    }

    @Test
    @DisplayName(value = "start testing process with loading questions error")
    void shouldNotDoTest_loadingQuestionsError() {
        final Student student = new Student("Ivan", "Ivanov");
        when(questionService.findAll()).thenThrow(new QuestionsLoadingException("Data file was stolen by thief", new IOException()));

        Throwable throwable = assertThrows(QuestionsLoadingException.class, () -> testingService.doTest(student));

        assertThat(throwable.getMessage()).isEqualTo("Data file was stolen by thief");
    }

}