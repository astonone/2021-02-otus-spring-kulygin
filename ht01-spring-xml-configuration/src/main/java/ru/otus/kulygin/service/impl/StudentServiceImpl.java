package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.UiService;

public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final UiService uiService;

    public StudentServiceImpl(StudentDao studentDao, UiService uiService) {
        this.studentDao = studentDao;
        this.uiService = uiService;
    }

    @Override
    public Student initStudent() {
        uiService.out("Enter your firstname:");
        String firstName = uiService.in();
        uiService.out("Enter your lastname:");
        String lastName = uiService.in();
        return studentDao.create(new Student(firstName, lastName));
    }

}
