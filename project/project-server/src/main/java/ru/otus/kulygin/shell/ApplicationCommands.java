package ru.otus.kulygin.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ApplicationCommands {

    // TODO с помощью Spring Batch сделать джобу, которая будет перегонять данные с вопросами для интервью из csv файла в базу
    @ShellMethod(value = "load csv interview questions to database", key = {"lq"})
    public String startLoadQuestionsJob() {
        return "";
    }

}
