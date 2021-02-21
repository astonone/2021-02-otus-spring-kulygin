package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.dao.StudentDAO;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public Student initStudent() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your firstname:");
        String firstName = bufferedReader.readLine();
        System.out.println("Enter your lastname:");
        String lastName = bufferedReader.readLine();
        return studentDAO.create(firstName, lastName);
    }

    @Override
    public void increaseStudentMark(Student student) {
        studentDAO.increaseMark(student);
    }
}
