package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.TestResultDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.TestResultService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName(value = "TestResultServiceImpl should ")
class TestResultServiceImplTest {

    private static TestResultDao testResultDao;
    private static TestResultService testResultService;

    @BeforeAll
    public static void init() {
        testResultDao = mock(TestResultDao.class);
        testResultService = new TestResultServiceImpl(testResultDao);
    }

    @Test
    @DisplayName(value = "increase mark of the student")
    void increaseStudentMark() {
        TestResult testResult = new TestResult(new Student("Ivan", "Ivanov"));

        testResultService.increaseStudentMark(testResult);

        verify(testResultDao).increaseStudentMark(testResult);
    }
}