package ru.otus.kulygin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.annotation.Measurable;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestingService;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final QuestionService questionService;
    private final UiFacade uiFacade;
    private final int markForPass;

    public TestingServiceImpl(QuestionService questionService, UiFacade uiFacade,
                              @Value("${test.mark.pass}") int markForPass) {
        this.questionService = questionService;
        this.uiFacade = uiFacade;
        this.markForPass = markForPass;
    }

    @Measurable
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
        uiFacade.showLocalizedMessageForUser("testing.welcome", student.getFirstName(), student.getLastName());
        uiFacade.showLocalizedMessageForUser(("testing.start"));
    }

    private void askStudent(int questionNumber, Question questionsAndAnswer) {
        uiFacade.showLocalizedMessageForUser("testing.question", String.valueOf(questionNumber));
        uiFacade.showMessageForUser(questionsAndAnswer.getQuestion());
        uiFacade.showLocalizedMessageForUser("testing.answer");
    }

    private String getStudentAnswer() {
        return uiFacade.getMessageFromUser();
    }

    private TestResult increaseStudentMarkIfCorrectAnswer(TestResult testResult, Question questionsAndAnswer, String answer) {
        if (answer.trim().equals(questionsAndAnswer.getAnswer())) {
            testResult.increaseStudentMark();
        }
        return testResult;
    }

    private void studentByeBye(TestResult testResult) {
        uiFacade.showLocalizedMessageForUser("testing.finish", String.valueOf(testResult.getMark()));

        checkTestResult(testResult);
    }

    private void checkTestResult(TestResult testResult) {
        if (testResult.getMark() >= markForPass) {
            uiFacade.showLocalizedMessageForUser("testing.pass");
        } else {
            uiFacade.showLocalizedMessageForUser("testing.notpass");
        }
    }

}
