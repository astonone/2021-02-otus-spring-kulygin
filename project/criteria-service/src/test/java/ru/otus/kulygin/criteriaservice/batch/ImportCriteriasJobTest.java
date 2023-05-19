package ru.otus.kulygin.criteriaservice.batch;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.criteriaservice.repository.InterviewTemplateCriteriaRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.kulygin.criteriaservice.configuration.TemplateCriteriaImportJobConfig.IMPORT_CRITERIAS_JOB;
import static ru.otus.kulygin.criteriaservice.configuration.TemplateCriteriaImportJobConfig.INPUT_FILE_NAME;

@ActiveProfiles("test")
@SpringBootTest
@SpringBatchTest
public class ImportCriteriasJobTest {

    private static final String TEST_INPUT_FILE_NAME = "test-criterias.csv";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository;

    @BeforeEach
    void clearMetaData() {
        interviewTemplateCriteriaRepository.deleteAll();
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        val expectedResult1 = InterviewTemplateCriteria.builder()
                .name("В чем разница между null и undefined?")
                .positionType("Java Script Developer")
                .build();

        val expectedResult2 = InterviewTemplateCriteria.builder()
                .name("Для чего используется оператор \"&&\"?")
                .positionType("Java Script Developer")
                .build();

        val expectedResult3 = InterviewTemplateCriteria.builder()
                .name("Для чего используется оператор \"||\"?")
                .positionType("Java Script Developer")
                .build();

        val expectedResult4 = InterviewTemplateCriteria.builder()
                .name("Является ли использование унарного плюса (оператор \"+\") самым быстрым способом преобразования строки в число?")
                .positionType("Java Script Developer")
                .build();

        val expectedResult5 = InterviewTemplateCriteria.builder()
                .name("Что такое DOM?")
                .positionType("Java Script Developer")
                .build();

        var classLoader = ImportCriteriasJobTest.class.getClassLoader();
        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8
        );

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_CRITERIAS_JOB);

        JobParameters parameters = new JobParametersBuilder()
                .addString(INPUT_FILE_NAME, testInputFileName).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        assertThat(interviewTemplateCriteriaRepository.existsByNameAndPositionType(expectedResult1.getName(), expectedResult1.getPositionType())).isTrue();
        assertThat(interviewTemplateCriteriaRepository.existsByNameAndPositionType(expectedResult2.getName(), expectedResult2.getPositionType())).isTrue();
        assertThat(interviewTemplateCriteriaRepository.existsByNameAndPositionType(expectedResult3.getName(), expectedResult3.getPositionType())).isTrue();
        assertThat(interviewTemplateCriteriaRepository.existsByNameAndPositionType(expectedResult4.getName(), expectedResult4.getPositionType())).isTrue();
        assertThat(interviewTemplateCriteriaRepository.existsByNameAndPositionType(expectedResult5.getName(), expectedResult5.getPositionType())).isTrue();
    }

}
