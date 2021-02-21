package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Student;

import java.io.IOException;

public interface TestingService {
    Student doTest(Student student) throws IOException;
}
