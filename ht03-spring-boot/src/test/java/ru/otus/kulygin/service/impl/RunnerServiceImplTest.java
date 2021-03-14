package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.service.RunnerService;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.TestingService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RunnerServiceImpl.class)
@DisplayName(value = "RunnerServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RunnerServiceImplTest {

    @Autowired
    private RunnerService runnerService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TestingService testingService;

    @Test
    @DisplayName(value = "run testing process")
    void shouldRun() {
        final Student student = new Student("Ivan", "Ivanov");
        when(studentService.initStudent()).thenReturn(student);

        runnerService.run();

        verify(studentService).initStudent();
        verify(testingService).doTest(student);
    }

    @Test
    @DisplayName(value = "not run testing process because student was not initialized")
    void shouldNotRun_studentInitError() {
        when(studentService.initStudent()).thenThrow(new UserInputException(new IOException("Houston!we have a problem!")));
        Throwable throwable = assertThrows(UserInputException.class, () -> runnerService.run());

        assertThat(throwable.getMessage()).isEqualTo("java.io.IOException: Houston!we have a problem!");
    }

}