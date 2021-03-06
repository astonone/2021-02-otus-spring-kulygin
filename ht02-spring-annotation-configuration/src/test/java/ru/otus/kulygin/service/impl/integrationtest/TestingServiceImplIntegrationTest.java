package ru.otus.kulygin.service.impl.integrationtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.kulygin.dao.impl.CsvQuestionDaoImpl;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestingService;
import ru.otus.kulygin.service.UiService;
import ru.otus.kulygin.service.impl.LocaleServiceImpl;
import ru.otus.kulygin.service.impl.QuestionServiceImpl;
import ru.otus.kulygin.service.impl.TestingServiceImpl;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName(value = "TestingServiceImplIntegrationTest should ")
public class TestingServiceImplIntegrationTest {
    private TestingService testingService;
    private UiService uiService;

    @BeforeEach
    public void init() {
        uiService = mock(UiService.class);
        QuestionService questionService = new QuestionServiceImpl(new CsvQuestionDaoImpl("integration-test-data/en.csv", ";"));
        LocaleService localeService = new LocaleServiceImpl(createMessageSource(), Locale.ENGLISH);
        testingService = new TestingServiceImpl(questionService, uiService, localeService, 4);
    }

    public ResourceBundleMessageSource createMessageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("i18n/message");
        ms.setDefaultEncoding("UTF-8");
        ms.setUseCodeAsDefaultMessage(true);
        return ms;
    }

    @Test
    @DisplayName(value = "start testing process and successfully finish it and pass it")
    void shouldDoTestAndPassIt() {
        final Student student = new Student("Ivan", "Ivanov");

        when(uiService.in())
                .thenAnswer(a -> "366")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "98")
                .thenAnswer(a -> "100");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(5);
        verify(uiService).out("Test passed");
    }

    @Test
    @DisplayName(value = "start testing process and successfully finish it and not pass it")
    void shouldDoTestAndNotPassIt() {
        final Student student = new Student("Ivan", "Ivanov");
        when(uiService.in())
                .thenAnswer(a -> "366")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "8")
                .thenAnswer(a -> "200");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(3);
        verify(uiService).out("Test was not passed");
    }
}
