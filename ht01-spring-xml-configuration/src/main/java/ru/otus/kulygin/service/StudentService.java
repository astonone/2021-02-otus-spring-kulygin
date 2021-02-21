package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Student;

import java.io.IOException;

public interface StudentService {
    Student initStudent() throws IOException;
    void increaseStudentMark(Student student);
}
