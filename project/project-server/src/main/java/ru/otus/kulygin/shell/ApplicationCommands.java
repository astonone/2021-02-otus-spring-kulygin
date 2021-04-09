package ru.otus.kulygin.shell;

import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.kulygin.service.InterviewService;
import ru.otus.kulygin.service.ObjectPrettyConverterService;

@ShellComponent
public class ApplicationCommands {

    private final InterviewService interviewService;
    private final ObjectPrettyConverterService objectPrettyConverterService;

    public ApplicationCommands(InterviewService interviewService, ObjectPrettyConverterService objectPrettyConverterService) {
        this.interviewService = interviewService;
        this.objectPrettyConverterService = objectPrettyConverterService;
    }

    @ShellMethod(value = "get all interviewers pageable(page: number, pageSize: number)", key = {"iga"})
    public String changeAppLocale(@ShellOption Integer page, @ShellOption Integer pageSize) {
        return objectPrettyConverterService.getPrettyString(interviewService.findAll(PageRequest.of(page, pageSize)));
    }

}
