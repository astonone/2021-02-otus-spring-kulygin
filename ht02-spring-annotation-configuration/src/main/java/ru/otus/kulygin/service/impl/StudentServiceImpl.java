package ru.otus.kulygin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.UiService;

import java.util.Locale;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final UiService uiService;
    private final MessageSource messageSource;
    private final Locale locale;

    public StudentServiceImpl(StudentDao studentDao, UiService uiService, MessageSource messageSource,
                              @Value("${app.locale}") Locale locale) {
        this.studentDao = studentDao;
        this.uiService = uiService;
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public Student initStudent() {
        uiService.out(messageSource.getMessage("student.fname.enter", null, locale));
        String firstName = uiService.in();
        uiService.out(messageSource.getMessage("student.lname.enter", null, locale));
        String lastName = uiService.in();
        return studentDao.create(new Student(firstName, lastName));
    }

}
