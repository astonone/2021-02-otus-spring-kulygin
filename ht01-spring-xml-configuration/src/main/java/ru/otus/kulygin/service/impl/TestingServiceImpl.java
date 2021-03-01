package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestingService;
import ru.otus.kulygin.service.UiService;

import java.util.List;

public class TestingServiceImpl implements TestingService {

    private final QuestionService questionService;
    private final UiService uiService;

    public TestingServiceImpl(QuestionService questionService, UiService uiService) {
        this.questionService = questionService;
        this.uiService = uiService;
    }

    @Override
    public TestResult doTest(Student student) {
        studentWelcome(student);

        List<Question> questionsAndAnswers = questionService.findAll();
        TestResult testResult = new TestResult(student);
        int questionNumber = 1;

        for (Question questionsAndAnswer : questionsAndAnswers) {

            askStudent(questionNumber, questionsAndAnswer);
            final String answer = getStudentAnswer();
            testResult = increaseStudentMarkIfCorrectAnswer(testResult, questionsAndAnswer, answer);

            questionNumber++;
        }
        studentByeBye(testResult);
        return testResult;
    }

    private void studentWelcome(Student student) {
        uiService.out("Welcome: " + student.getFirstName() + " " + student.getLastName());
        uiService.out("Testing started");
    }

    private void askStudent(int questionNumber, Question questionsAndAnswer) {
        uiService.out(questionNumber + " question:");
        uiService.out(questionsAndAnswer.getQuestion());
        uiService.out("Enter your answer:");
    }

    private String getStudentAnswer() {
        return uiService.in();
    }

    private TestResult increaseStudentMarkIfCorrectAnswer(TestResult testResult, Question questionsAndAnswer, String answer) {
        if (answer.trim().equals(questionsAndAnswer.getAnswer())) {
            testResult.increaseStudentMark();
        }
        return testResult;
    }

    private void studentByeBye(TestResult testResult) {
        uiService.out("Testing has been finished, your mark: " + testResult.getMark());
    }

}
