package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final UiFacade uiFacade;

    public StudentServiceImpl(StudentDao studentDao, UiFacade uiFacade) {
        this.studentDao = studentDao;
        this.uiFacade = uiFacade;
    }

    @Override
    public Student initStudent() {
        uiFacade.showLocalizedMessageForUser("student.fname.enter");
        String firstName = uiFacade.getMessageFromUser();
        uiFacade.showLocalizedMessageForUser("student.lname.enter");
        String lastName = uiFacade.getMessageFromUser();
        return studentDao.create(new Student(firstName, lastName));
    }

}
