package ru.otus.kulygin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestingService;
import ru.otus.kulygin.service.UiService;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final QuestionService questionService;
    private final UiService uiService;
    private final LocaleService localeService;
    private final int markForPass;

    public TestingServiceImpl(QuestionService questionService, UiService uiService, LocaleService localeService,
                              @Value("${test.mark.pass}") int markForPass) {
        this.questionService = questionService;
        this.uiService = uiService;
        this.localeService = localeService;
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
        uiService.out(localeService.getLocalizedString("testing.welcome") + student.getFirstName() + " " + student.getLastName());
        uiService.out(localeService.getLocalizedString(("testing.start")));
    }

    private void askStudent(int questionNumber, Question questionsAndAnswer) {
        uiService.out(questionNumber + " " + localeService.getLocalizedString("testing.question"));
        uiService.out(questionsAndAnswer.getQuestion());
        uiService.out(localeService.getLocalizedString("testing.answer"));
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
        uiService.out(localeService.getLocalizedString("testing.finish") + testResult.getMark());

        checkTestResult(testResult);
    }

    private void checkTestResult(TestResult testResult) {
        if (testResult.getMark() >= markForPass) {
            uiService.out(localeService.getLocalizedString("testing.pass"));
        } else {
            uiService.out(localeService.getLocalizedString("testing.notpass"));
        }
    }

}
