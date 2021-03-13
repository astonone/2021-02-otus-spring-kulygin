package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;

public interface TestingService {

    TestResult doTest(Student student);

}
