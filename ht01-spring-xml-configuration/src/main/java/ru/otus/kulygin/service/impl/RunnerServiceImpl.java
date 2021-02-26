package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.RunnerService;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.TestingService;

public class RunnerServiceImpl implements RunnerService {

    private final StudentService studentService;
    private final TestingService testingService;

    public RunnerServiceImpl(StudentService studentService, TestingService testingService) {
        this.studentService = studentService;
        this.testingService = testingService;
    }

    @Override
    public TestResult run() {
        Student student = studentService.initStudent();
        return testingService.doTest(student);
    }

}
