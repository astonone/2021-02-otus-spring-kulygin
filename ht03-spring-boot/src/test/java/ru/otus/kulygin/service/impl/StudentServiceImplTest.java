package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.StudentService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = StudentServiceImpl.class)
@DisplayName(value = "StudentServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentDao studentDao;

    @MockBean
    private UiFacade uiFacade;

    @Test
    @DisplayName(value = "init student")
    void shouldInitStudent() {
        final Student student = new Student("Ivan", "Ivanov");
        when(uiFacade.getMessageFromUser()).thenAnswer(a -> "Ivan").thenAnswer(a -> "Ivanov");
        when(studentDao.create(student)).thenReturn(student);

        final Student result = studentService.initStudent();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(student);
        verify(uiFacade, times(2)).getMessageFromUser();
        verify(studentDao).create(student);
    }

    @Test
    @DisplayName(value = "not init student")
    void shouldNotInitStudent() {
        when(uiFacade.getMessageFromUser()).thenThrow(new UserInputException(new IOException("Houston!we have a problem!")));
        Throwable throwable = assertThrows(UserInputException.class, () -> studentService.initStudent());

        assertThat(throwable.getMessage()).isEqualTo("java.io.IOException: Houston!we have a problem!");
    }

}