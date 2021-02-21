package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Student;

public interface StudentDAO {
    Student create(String firstName, String lastName);
    void increaseMark(Student student);
}
