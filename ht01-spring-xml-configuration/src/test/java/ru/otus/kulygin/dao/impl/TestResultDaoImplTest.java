package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.TestResultDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "TestResultDao should ")
class TestResultDaoImplTest {

    private final TestResultDao testResultDao = new TestResultDaoImpl();

    @Test
    @DisplayName(value = "increase of the student mark")
    void shouldIncreaseStudentMark() {
        final Student student = new Student("Ivan", "Ivanov");
        final TestResult testResult = new TestResult(student);
        testResult.setMark(2);

        final TestResult result = testResultDao.increaseStudentMark(testResult);

        assertThat(result.getStudent()).isEqualTo(student);
        assertThat(result.getMark()).isEqualTo(3);
    }
}