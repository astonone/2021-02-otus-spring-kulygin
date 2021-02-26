package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.dao.TestResultDao;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.TestResultService;

public class TestResultServiceImpl implements TestResultService {

    private final TestResultDao testResultDao;

    public TestResultServiceImpl(TestResultDao testResultDao) {
        this.testResultDao = testResultDao;
    }

    @Override
    public TestResult increaseStudentMark(TestResult testResult) {
        return testResultDao.increaseStudentMark(testResult);
    }

}
