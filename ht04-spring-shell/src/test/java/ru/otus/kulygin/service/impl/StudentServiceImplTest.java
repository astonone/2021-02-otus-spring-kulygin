package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = StudentServiceImpl.class)
@DisplayName(value = "StudentServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentDao studentDao;

    @Test
    @DisplayName(value = "init student")
    void shouldInitStudent() {
        final Student student = new Student("Ivan", "Ivanov");
        when(studentDao.create(student)).thenReturn(student);

        final Student result = studentService.initStudent("Ivan", "Ivanov");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(student);
        verify(studentDao).create(student);
    }

}