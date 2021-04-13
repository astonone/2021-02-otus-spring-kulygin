package ru.otus.kulygin.shell;

import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.kulygin.service.InterviewerService;
import ru.otus.kulygin.service.ObjectPrettyConverterService;

@ShellComponent
public class ApplicationCommands {

    private final InterviewerService interviewerService;
    private final ObjectPrettyConverterService objectPrettyConverterService;

    public ApplicationCommands(InterviewerService interviewerService, ObjectPrettyConverterService objectPrettyConverterService) {
        this.interviewerService = interviewerService;
        this.objectPrettyConverterService = objectPrettyConverterService;
    }

    @ShellMethod(value = "get all interviewers pageable(page: number, pageSize: number)", key = {"iga"})
    public String changeAppLocale(@ShellOption Integer page, @ShellOption Integer pageSize) {
        return objectPrettyConverterService.getPrettyString(interviewerService.findAll(PageRequest.of(page, pageSize)));
    }

}
