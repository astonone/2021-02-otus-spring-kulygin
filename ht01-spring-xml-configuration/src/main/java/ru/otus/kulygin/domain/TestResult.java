package ru.otus.kulygin.domain;

import java.util.Objects;

public class TestResult {

    private final Student student;
    private int mark;

    public TestResult(Student student) {
        this.student = student;
        this.mark = 0;
    }

    public Student getStudent() {
        return student;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void increaseStudentMark() {
        this.mark++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestResult that = (TestResult) o;
        return mark == that.mark && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, mark);
    }
}
