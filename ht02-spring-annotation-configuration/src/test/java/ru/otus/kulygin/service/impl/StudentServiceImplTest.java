package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.UiService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName(value = "StudentServiceImpl should ")
class StudentServiceImplTest {

    private StudentService studentService;
    private StudentDao studentDao;
    private UiService uiService;
    private LocaleService localeService;

    @BeforeEach
    public void init() {
        uiService = mock(UiService.class);
        studentDao = mock(StudentDao.class);
        localeService = mock(LocaleService.class);
        studentService = new StudentServiceImpl(studentDao, uiService, localeService);
    }

    @Test
    @DisplayName(value = "init student")
    void shouldInitStudent() {
        uiService = mock(UiService.class);
        studentDao = mock(StudentDao.class);
        studentService = new StudentServiceImpl(studentDao, uiService, localeService);

        final Student student = new Student("Ivan", "Ivanov");
        when(uiService.in()).thenAnswer(a -> "Ivan").thenAnswer(a -> "Ivanov");
        when(studentDao.create(student)).thenReturn(student);

        final Student result = studentService.initStudent();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(student);
        verify(uiService, times(2)).in();
        verify(studentDao).create(student);
    }

    @Test
    @DisplayName(value = "not init student")
    void shouldNotInitStudent() {
        when(uiService.in()).thenThrow(new UserInputException(new IOException("Houston!we have a problem!")));
        Throwable throwable = assertThrows(UserInputException.class, () -> studentService.initStudent());

        assertThat(throwable.getMessage()).isEqualTo("java.io.IOException: Houston!we have a problem!");
    }
}