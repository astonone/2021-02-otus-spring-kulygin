package ru.otus.kulygin.criteriaservice.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.criteriaservice.BaseTest;
import ru.otus.kulygin.criteriaservice.service.BatchService;
import ru.otus.kulygin.criteriaservice.service.StorageService;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static ru.otus.kulygin.criteriaservice.configuration.TemplateCriteriaImportJobConfig.IMPORT_CRITERIAS_JOB;
import static ru.otus.kulygin.criteriaservice.configuration.TemplateCriteriaImportJobConfig.INPUT_FILE_NAME;


@ActiveProfiles("test")
@SpringBootTest(classes = BatchServiceImpl.class)
class BatchServiceImplTest extends BaseTest {

    @Autowired
    private BatchService batchService;

    @MockBean
    private JobOperator jobOperator;

    @MockBean
    private JobExplorer jobExplorer;

    @MockBean
    private StorageService storageService;

    @Test
    void shouldStartImportCriteriasJob() throws IOException, JobInstanceAlreadyExistsException, NoSuchJobException, JobParametersInvalidException {
        val mockCsvMultipartFile = getMockCsvMultipartFile();
        val file = mock(File.class);
        when(file.getAbsolutePath()).thenReturn("test.csv");
        when(storageService.store(mockCsvMultipartFile)).thenReturn(file);

        batchService.startImportCriteriasJob(mockCsvMultipartFile);

        verify(storageService).store(mockCsvMultipartFile);
        verify(jobOperator).start(IMPORT_CRITERIAS_JOB,
                INPUT_FILE_NAME + "=" + "test.csv" +
                        ", jobStartCount" + "=" + 1);
    }

    @Test
    void shouldGetLastJobExecutionInfo() throws NoSuchJobExecutionException {
        val lastJobInstance = new JobInstance(1L, IMPORT_CRITERIAS_JOB);
        when(jobExplorer.getLastJobInstance(IMPORT_CRITERIAS_JOB)).thenReturn(lastJobInstance);
        when(jobOperator.getSummary(lastJobInstance.getInstanceId())).thenReturn("this my job result");

        val lastJobExecutionInfo = batchService.getLastJobExecutionInfo();

        assertThat(lastJobExecutionInfo).isEqualTo("this my job result");
    }

    @Test
    void shouldNotGetLastJobExecutionInfo() {
        assertThatThrownBy(() -> batchService.getLastJobExecutionInfo())
                .isInstanceOf(NoSuchJobExecutionException.class);
    }
}