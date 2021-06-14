package ru.otus.kulygin.shell;

import org.h2.tools.Console;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

import static ru.otus.kulygin.config.JobConfig.IMPORT_BOOK_JOB_NAME;

@ShellComponent
public class BatchCommands {

    private final Job importBookJob;

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    public BatchCommands(Job importBookJob, JobOperator jobOperator, JobExplorer jobExplorer) {
        this.importBookJob = importBookJob;
        this.jobOperator = jobOperator;
        this.jobExplorer = jobExplorer;
    }

    @ShellMethod(value = "Open web console", key = {"owc", "open-web-console"})
    public String openWebConsole() throws SQLException {
        Console.main();
        return "Web console starting...";
    }

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        final JobInstance lastJobInstance = jobExplorer.getLastJobInstance(IMPORT_BOOK_JOB_NAME);
        if (lastJobInstance == null) {
            Long executionId = jobOperator.start(IMPORT_BOOK_JOB_NAME, "");
            System.out.println(jobOperator.getSummary(executionId));
        } else {
            final Long nextInstanceExecutionId = jobOperator.startNextInstance(IMPORT_BOOK_JOB_NAME);
            System.out.println(jobOperator.getSummary(nextInstanceExecutionId));
        }

    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_BOOK_JOB_NAME));
    }

}
