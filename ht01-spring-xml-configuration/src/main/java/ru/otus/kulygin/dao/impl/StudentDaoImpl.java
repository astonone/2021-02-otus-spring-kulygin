package ru.otus.kulygin.dao.impl;

import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;

public class StudentDaoImpl implements StudentDao {

    @Override
    public Student create(Student student) {
        return new Student(student.getFirstName(), student.getLastName());
    }

}
