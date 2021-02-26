package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestResultService;
import ru.otus.kulygin.service.TestingService;
import ru.otus.kulygin.service.UiService;

import java.util.List;

public class TestingServiceImpl implements TestingService {

    private final QuestionService questionService;
    private final UiService uiService;
    private final TestResultService testResultService;

    public TestingServiceImpl(QuestionService questionService, UiService uiService, TestResultService testResultService) {
        this.questionService = questionService;
        this.uiService = uiService;
        this.testResultService = testResultService;
    }

    @Override
    public TestResult doTest(Student student) {
        studentWelcome(student);

        List<Question> questionsAndAnswers = questionService.findAll();
        TestResult testResult = new TestResult(student);
        int questionNumber = 1;

        for (Question questionsAndAnswer : questionsAndAnswers) {

            uiService.out(questionNumber + " question:");
            uiService.out(questionsAndAnswer.getQuestion());
            uiService.out("Enter your answer:");

            final String answer = uiService.in();

            if (answer.trim().equals(questionsAndAnswer.getAnswer())) {
                testResult = testResultService.increaseStudentMark(testResult);
            }

            questionNumber++;
        }
        studentByeBye(testResult);
        return testResult;
    }

    private void studentWelcome(Student student) {
        uiService.out("Welcome: " + student.getFirstName() + " " + student.getLastName());
        uiService.out("Testing started");
    }

    private void studentByeBye(TestResult testResult) {
        uiService.out("Testing has been finished, your mark: " + testResult.getMark());
    }

}
