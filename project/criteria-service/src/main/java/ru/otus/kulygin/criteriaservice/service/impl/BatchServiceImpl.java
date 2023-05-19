package ru.otus.kulygin.criteriaservice.service.impl;

import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.criteriaservice.service.BatchService;
import ru.otus.kulygin.criteriaservice.service.StorageService;

import java.io.File;

import static ru.otus.kulygin.criteriaservice.configuration.TemplateCriteriaImportJobConfig.IMPORT_CRITERIAS_JOB;
import static ru.otus.kulygin.criteriaservice.configuration.TemplateCriteriaImportJobConfig.INPUT_FILE_NAME;

@Service
public class BatchServiceImpl implements BatchService {

    private static long jobStartCount = 1L;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;
    private final StorageService storageService;

    public BatchServiceImpl(JobOperator jobOperator, JobExplorer jobExplorer, StorageService storageService) {
        this.jobOperator = jobOperator;
        this.jobExplorer = jobExplorer;
        this.storageService = storageService;
    }

    @Override
    public void startImportCriteriasJob(MultipartFile multipartFile) throws JobInstanceAlreadyExistsException, NoSuchJobException, JobParametersInvalidException {
        File file = storageService.store(multipartFile);

        jobOperator.start(IMPORT_CRITERIAS_JOB,
                INPUT_FILE_NAME + "=" + file.getAbsolutePath() +
                        ", jobStartCount" + "=" + jobStartCount++);
    }

    @Override
    public String getLastJobExecutionInfo() throws NoSuchJobExecutionException {
        final JobInstance lastJobInstance = jobExplorer.getLastJobInstance(IMPORT_CRITERIAS_JOB);
        if (lastJobInstance == null) {
            throw new NoSuchJobExecutionException("Job instance was not found");
        }
        return jobOperator.getSummary(lastJobInstance.getInstanceId());
    }
}
