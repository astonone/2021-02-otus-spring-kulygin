package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.UiService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName(value = "StudentServiceImpl should ")
class StudentServiceImplTest {

    private static StudentService studentService;
    private static StudentDao studentDao;
    private static UiService uiService;

    @BeforeAll
    public static void init() {
        uiService = mock(UiService.class);
        studentDao = mock(StudentDao.class);
        studentService = new StudentServiceImpl(studentDao, uiService);
    }

    @Test
    @DisplayName(value = "init student")
    void shouldInitStudent() {
        final Student student = new Student("Ivan", "Ivanov");
        when(uiService.in()).thenAnswer(a -> "Ivan").thenAnswer(a -> "Ivanov");
        when(studentDao.create(student)).thenReturn(student);

        final Student result = studentService.initStudent();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(student);
        verify(uiService, times(2)).in();
        verify(studentDao).create(student);
    }
}