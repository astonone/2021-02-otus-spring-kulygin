package ru.otus.kulygin;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.TestingService;

import java.io.IOException;

public class HT01SpringXmlConfiguration {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");

        TestingService testingService = applicationContext.getBean(TestingService.class);
        StudentService studentService = applicationContext.getBean(StudentService.class);

        Student student = studentService.initStudent();
        testingService.doTest(student);
    }
}
