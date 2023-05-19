package ru.otus.kulygin.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.StudentService;
import ru.otus.kulygin.service.TestingService;

@ShellComponent
public class ApplicationCommands {

    private final StudentService studentService;
    private final TestingService testingService;
    private final UiFacade uiFacade;

    private Student loggedStudent;

    public ApplicationCommands(StudentService studentService, TestingService testingService, UiFacade uiFacade) {
        this.studentService = studentService;
        this.testingService = testingService;
        this.uiFacade = uiFacade;
    }

    @ShellMethod(value = "Login student command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "AnyFirstName") String firstName,
                      @ShellOption(defaultValue = "AnyLastName") String lastName) {
        loggedStudent = studentService.initStudent(firstName, lastName);
        uiFacade.showLocalizedMessageForUser("login.finish", loggedStudent.getFirstName(),
                loggedStudent.getLastName());
    }

    @ShellMethod(value = "Start testing of logged student command", key = {"t", "test"})
    @ShellMethodAvailability(value = "isTestingCommandAvailable")
    public void testing() {
        testingService.doTest(loggedStudent);
    }

    private Availability isTestingCommandAvailable() {
        return loggedStudent == null ?
                Availability.unavailable(uiFacade.getLocalizedMessageForUser("login.required"))
                : Availability.available();
    }

}
