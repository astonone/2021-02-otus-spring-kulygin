package ru.otus.kulygin.criteriaservice.service;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.web.multipart.MultipartFile;

public interface BatchService {

    void startImportCriteriasJob(MultipartFile multipartFile) throws JobInstanceAlreadyExistsException, NoSuchJobException, JobParametersInvalidException;

    String getLastJobExecutionInfo() throws NoSuchJobExecutionException;

}
