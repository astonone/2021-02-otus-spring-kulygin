package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student initStudent(String firstName, String lastName) {
        return studentDao.create(new Student(firstName, lastName));
    }

}
