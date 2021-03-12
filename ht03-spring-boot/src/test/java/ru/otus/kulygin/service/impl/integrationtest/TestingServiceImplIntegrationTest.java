package ru.otus.kulygin.service.impl.integrationtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.TestingService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName(value = "TestingServiceImplIntegrationTest should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestingServiceImplIntegrationTest {

    @Autowired
    private TestingService testingService;

    @MockBean
    private UiFacade uiFacade;

    @Test
    @DisplayName(value = "start testing process and successfully finish it and pass it")
    void shouldDoTestAndPassIt() {
        final Student student = new Student("Ivan", "Ivanov");

        when(uiFacade.getMessageFromUser())
                .thenAnswer(a -> "366")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "98")
                .thenAnswer(a -> "100");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(5);
        verify(uiFacade).showLocalizedMessageForUser("testing.pass");
    }

    @Test
    @DisplayName(value = "start testing process and successfully finish it and not pass it")
    void shouldDoTestAndNotPassIt() {
        final Student student = new Student("Ivan", "Ivanov");
        when(uiFacade.getMessageFromUser())
                .thenAnswer(a -> "366")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "8")
                .thenAnswer(a -> "200");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(3);
        verify(uiFacade).showLocalizedMessageForUser("testing.notpass");
    }

}
