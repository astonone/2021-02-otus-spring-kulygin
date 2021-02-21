package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.StudentDAO;
import ru.otus.kulygin.domain.Student;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName(value = "StudentDAO should ")
class StudentDAOImplTest {

    private final StudentDAO studentDAO = new StudentDAOImpl();;

    @Test
    @DisplayName(value = "create student")
    void shouldCreateStudent() {
        final Student student = studentDAO.create("Ivan", "Ivanov");

        assertThat(student.getFirstName(), equalTo("Ivan"));
        assertThat(student.getLastName(), equalTo("Ivanov"));
        assertThat(student.getMark(), equalTo(0));
    }

    @Test
    @DisplayName(value = "increase of student mark")
    void shouldIncreaseMark() {
        final Student student = studentDAO.create("Ivan", "Ivanov");

        studentDAO.increaseMark(student);

        assertThat(student.getMark(), equalTo(1));
    }
}