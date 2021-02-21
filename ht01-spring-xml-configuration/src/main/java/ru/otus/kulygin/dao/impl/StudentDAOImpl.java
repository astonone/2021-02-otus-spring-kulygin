package ru.otus.kulygin.dao.impl;

import ru.otus.kulygin.dao.StudentDAO;
import ru.otus.kulygin.domain.Student;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public Student create(String firstName, String lastName) {
        return new Student(firstName, lastName);
    }

    @Override
    public void increaseMark(Student student) {
        student.setMark(student.getMark() + 1);
    }
}
