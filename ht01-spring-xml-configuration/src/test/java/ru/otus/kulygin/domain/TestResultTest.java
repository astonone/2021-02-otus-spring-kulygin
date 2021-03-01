package ru.otus.kulygin.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "TestResult should ")
class TestResultTest {

    @Test
    @DisplayName(value = "increase mark of the student")
    void increaseStudentMark() {
        TestResult testResult = new TestResult(new Student("Ivan", "Ivanov"));
        testResult.setMark(2);

        testResult.increaseStudentMark();

        assertThat(testResult.getMark()).isEqualTo(3);
    }

}