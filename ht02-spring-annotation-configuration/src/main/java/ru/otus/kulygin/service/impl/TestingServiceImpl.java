package ru.otus.kulygin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestingService;
import ru.otus.kulygin.service.UiService;

import java.util.List;
import java.util.Locale;

@Service
public class TestingServiceImpl implements TestingService {

    private final QuestionService questionService;
    private final UiService uiService;
    private final MessageSource messageSource;
    private final Locale locale;
    private final int markForPass;

    public TestingServiceImpl(QuestionService questionService, UiService uiService, MessageSource messageSource,
                              @Value("${app.locale}") Locale locale,
                              @Value("${test.mark.pass}") int markForPass) {
        this.questionService = questionService;
        this.uiService = uiService;
        this.messageSource = messageSource;
        this.locale = locale;
        this.markForPass = markForPass;
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
        uiService.out(messageSource.getMessage("testing.welcome", null, locale) + student.getFirstName() + " " + student.getLastName());
        uiService.out(messageSource.getMessage("testing.start", null, locale));
    }

    private void askStudent(int questionNumber, Question questionsAndAnswer) {
        uiService.out(questionNumber + " " + messageSource.getMessage("testing.question", null, locale));
        uiService.out(questionsAndAnswer.getQuestion());
        uiService.out(messageSource.getMessage("testing.answer", null, locale));
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
        uiService.out(messageSource.getMessage("testing.finish", null, locale) + testResult.getMark());

        checkTestResult(testResult);
    }

    private void checkTestResult(TestResult testResult) {
        if (testResult.getMark() >= markForPass) {
            uiService.out(messageSource.getMessage("testing.pass", null, locale));
        } else {
            uiService.out(messageSource.getMessage("testing.notpass", null, locale));
        }
    }

}
