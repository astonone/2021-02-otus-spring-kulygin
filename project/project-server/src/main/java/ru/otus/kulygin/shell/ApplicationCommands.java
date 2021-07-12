/*
package ru.otus.kulygin.shell;

import org.h2.tools.Console;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.sql.SQLException;

import static ru.otus.kulygin.config.JobConfig.IMPORT_CRITERIAS_JOB;
import static ru.otus.kulygin.config.JobConfig.INPUT_FILE_NAME;

@ShellComponent
public class ApplicationCommands {

    private static long jobStartCount = 1L;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    public ApplicationCommands(JobOperator jobOperator, JobExplorer jobExplorer) {
        this.jobOperator = jobOperator;
        this.jobExplorer = jobExplorer;
    }

    @ShellMethod(value = "open web console", key = "owc")
    public void openWebConsole() throws SQLException {
        Console.main();
        System.out.println("Console opening");
    }

    @ShellMethod(value = "load csv interview questions to database(String inputFilePath)", key = "lc")
    public void startMigrationJobWithJobOperator(@ShellOption String inputFilePath) throws Exception {
        Long executionId = jobOperator.start(IMPORT_CRITERIAS_JOB,
                INPUT_FILE_NAME + "=" + inputFilePath +
                        ", jobStartCount" + "=" + jobStartCount++);

        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "show info", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_CRITERIAS_JOB));
    }

}
*/
