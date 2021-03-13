package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName(value = "StudentServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentServiceImplTest {

    @Configuration
    static class NestedConfiguration {
        @Bean
        StudentDao studentDao() {
            return mock(StudentDao.class);
        }

        @Bean
        StudentService studentService() {
            return new StudentServiceImpl(studentDao());
        }
    }

    @Autowired
    private StudentService studentService;

    @Autowired
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