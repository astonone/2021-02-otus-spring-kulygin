package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.service.RunnerService;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.TestingService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName(value = "RunnerServiceImpl should ")
class RunnerServiceImplTest {

    private static RunnerService runnerService;
    private static StudentService studentService;
    private static TestingService testingService;

    @BeforeAll
    public static void init() {
        studentService = mock(StudentService.class);
        testingService = mock(TestingService.class);
        runnerService = new RunnerServiceImpl(studentService, testingService);
    }

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