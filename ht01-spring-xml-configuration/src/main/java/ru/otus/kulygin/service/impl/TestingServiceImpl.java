package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.domain.QuestionAndAnswer;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.DataService;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.TestingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestingServiceImpl implements TestingService {

    private final DataService dataService;
    private final StudentService studentService;

    public TestingServiceImpl(DataService dataService, StudentService studentService) {
        this.dataService = dataService;
        this.studentService = studentService;
    }

    @Override
    public Student doTest(Student student) throws IOException {
        System.out.println("Welcome: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Testing started");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<QuestionAndAnswer> questionsAndAnswers = dataService.getQuestionsAndAnswers();

        int questionNumber = 1;
        for (QuestionAndAnswer questionsAndAnswer : questionsAndAnswers) {

            System.out.println(questionNumber + " question:");
            System.out.println(questionsAndAnswer.getQuestion());

            System.out.println("Enter your answer:");
            String answer = bufferedReader.readLine();

            if (answer.trim().equals(questionsAndAnswer.getAnswer())) {
                studentService.increaseStudentMark(student);
            }

            questionNumber++;
        }
        System.out.println("Testing has been finished, your mark: " + student.getMark());
        return student;
    }
}
