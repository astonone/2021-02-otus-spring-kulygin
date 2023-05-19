package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.StudentDao;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.UiService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final UiService uiService;
    private final LocaleService localeService;

    public StudentServiceImpl(StudentDao studentDao, UiService uiService, LocaleService localeService) {
        this.studentDao = studentDao;
        this.uiService = uiService;
        this.localeService = localeService;
    }

    @Override
    public Student initStudent() {
        uiService.out(localeService.getLocalizedString("student.fname.enter"));
        String firstName = uiService.in();
        uiService.out(localeService.getLocalizedString("student.lname.enter"));
        String lastName = uiService.in();
        return studentDao.create(new Student(firstName, lastName));
    }

}
