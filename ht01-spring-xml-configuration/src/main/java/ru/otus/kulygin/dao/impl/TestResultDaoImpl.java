package ru.otus.kulygin.dao.impl;

import ru.otus.kulygin.dao.TestResultDao;
import ru.otus.kulygin.domain.TestResult;

public class TestResultDaoImpl implements TestResultDao {

    @Override
    public TestResult increaseStudentMark(TestResult testResult) {
        TestResult result = new TestResult(testResult.getStudent());
        result.setMark(testResult.getMark() + 1);
        return result;
    }

}
