package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = StudentDaoImpl.class)
@DisplayName(value = "StudentDao should ")
class StudentDaoImplTest {

    @Autowired
    private StudentDao studentDAO;

    @Test
    @DisplayName(value = "create student")
    void shouldCreateStudent() {
        final Student student = new Student("Ivan", "Ivanov");
        final Student result = studentDAO.create(student);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(student.getFirstName());
        assertThat(result.getLastName()).isEqualTo(student.getLastName());
    }

}