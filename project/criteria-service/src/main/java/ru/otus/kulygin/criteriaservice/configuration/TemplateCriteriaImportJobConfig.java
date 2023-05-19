package ru.otus.kulygin.criteriaservice.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.criteriaservice.repository.InterviewTemplateCriteriaRepository;
import ru.otus.kulygin.criteriaservice.service.InterviewTemplateCriteriaService;

@Configuration
public class TemplateCriteriaImportJobConfig {

    private static final int CHUNK_SIZE = 10;

    public static final String INPUT_FILE_NAME = "inputFileName";
    public static final String IMPORT_CRITERIAS_JOB = "importCriteriasJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public TemplateCriteriaImportJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @StepScope
    @Bean
    public FlatFileItemReader<InterviewTemplateCriteria> reader(@Value("#{jobParameters['" + INPUT_FILE_NAME + "']}") String inputFileName) {
        return new FlatFileItemReaderBuilder<InterviewTemplateCriteria>()
                .name("interviewTemplateCriteriaItemReader")
                .resource(new FileSystemResource(inputFileName))
                .lineMapper((s, i) -> {
                    String[] fieldsValues = s.split(";");
                    return InterviewTemplateCriteria.builder()
                            .name(fieldsValues[0])
                            .positionType(fieldsValues[1])
                            .build();
                })
                .build();
    }

    @Bean
    public ItemProcessor<InterviewTemplateCriteria, InterviewTemplateCriteria> processor(InterviewTemplateCriteriaService interviewTemplateCriteriaService) {
        return interviewTemplateCriteriaService::processItem;
    }

    @Bean
    public RepositoryItemWriter<InterviewTemplateCriteria> interviewTemplateCriteriaWriter(InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository) {
        return new RepositoryItemWriterBuilder<InterviewTemplateCriteria>()
                .repository(interviewTemplateCriteriaRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public Job importUserJob(Step transformCriteriaStep) {
        return jobBuilderFactory.get(IMPORT_CRITERIAS_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(transformCriteriaStep)
                .end()
                .build();
    }

    @Bean
    public Step transformCriteriaStep(ItemReader<InterviewTemplateCriteria> reader, RepositoryItemWriter<InterviewTemplateCriteria> writer,
                                      ItemProcessor<InterviewTemplateCriteria, InterviewTemplateCriteria> itemProcessor) {
        return stepBuilderFactory.get("loadCriteriasFromFile")
                .<InterviewTemplateCriteria, InterviewTemplateCriteria>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

}
