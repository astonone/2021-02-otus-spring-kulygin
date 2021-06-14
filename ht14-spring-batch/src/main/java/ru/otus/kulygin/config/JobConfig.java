package ru.otus.kulygin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.lang.NonNull;
import ru.otus.kulygin.domain.mongodb.AuthorDocument;
import ru.otus.kulygin.domain.mongodb.BookDocument;
import ru.otus.kulygin.domain.mongodb.CommentDocument;
import ru.otus.kulygin.domain.mongodb.GenreDocument;
import ru.otus.kulygin.domain.rdb.Author;
import ru.otus.kulygin.domain.rdb.Book;
import ru.otus.kulygin.domain.rdb.Comment;
import ru.otus.kulygin.domain.rdb.Genre;
import ru.otus.kulygin.repository.mongodb.AuthorDocumentRepository;
import ru.otus.kulygin.repository.mongodb.BookDocumentRepository;
import ru.otus.kulygin.repository.mongodb.CommentDocumentRepository;
import ru.otus.kulygin.repository.mongodb.GenreDocumentRepository;
import ru.otus.kulygin.repository.rdb.AuthorRepository;
import ru.otus.kulygin.repository.rdb.BookRepository;
import ru.otus.kulygin.repository.rdb.CommentRepository;
import ru.otus.kulygin.repository.rdb.GenreRepository;
import ru.otus.kulygin.service.AuthorTransformerService;
import ru.otus.kulygin.service.BookTransformerService;
import ru.otus.kulygin.service.CommentTransformerService;
import ru.otus.kulygin.service.GenreTransformerService;

import java.util.HashMap;

@Configuration
public class JobConfig {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private static final int CHUNK_SIZE = 5;
    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public RepositoryItemReader<Genre> genreReader(GenreRepository genreRepository) {
        return new RepositoryItemReaderBuilder<Genre>()
                .name("genreRepositoryItemReader")
                .repository(genreRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public RepositoryItemReader<Author> authorReader(AuthorRepository authorRepository) {
        return new RepositoryItemReaderBuilder<Author>()
                .name("authorRepositoryItemReader")
                .repository(authorRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public RepositoryItemReader<Book> bookReader(BookRepository bookRepository) {
        return new RepositoryItemReaderBuilder<Book>()
                .name("bookRepositoryItemReader")
                .repository(bookRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public RepositoryItemReader<Comment> commentReader(CommentRepository commentRepository) {
        return new RepositoryItemReaderBuilder<Comment>()
                .name("commentRepositoryItemReader")
                .repository(commentRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Genre, GenreDocument> genreProcessor(GenreTransformerService genreTransformerService) {
        return genreTransformerService::transform;
    }

    @Bean
    public ItemProcessor<Author, AuthorDocument> authorProcessor(AuthorTransformerService authorTransformerService) {
        return authorTransformerService::transform;
    }

    @Bean
    public ItemProcessor<Book, BookDocument> bookProcessor(BookTransformerService bookTransformerService) {
        return bookTransformerService::transform;
    }

    @Bean
    public ItemProcessor<Comment, CommentDocument> commentProcessor(CommentTransformerService commentTransformerService) {
        return commentTransformerService::transform;
    }

    @Bean
    public RepositoryItemWriter<GenreDocument> genreWriter(GenreDocumentRepository genreDocumentRepository) {
        return new RepositoryItemWriterBuilder<GenreDocument>()
                .repository(genreDocumentRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public RepositoryItemWriter<AuthorDocument> authorWriter(AuthorDocumentRepository authorDocumentRepository) {
        return new RepositoryItemWriterBuilder<AuthorDocument>()
                .repository(authorDocumentRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public RepositoryItemWriter<BookDocument> bookWriter(BookDocumentRepository bookDocumentRepository) {
        return new RepositoryItemWriterBuilder<BookDocument>()
                .repository(bookDocumentRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public RepositoryItemWriter<CommentDocument> commentWriter(CommentDocumentRepository commentDocumentRepository) {
        return new RepositoryItemWriterBuilder<CommentDocument>()
                .repository(commentDocumentRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public Job importBookJob(Step transformGenreStep, Step transformAuthorStep, Step transformBookStep,
                             Step transformCommentStep) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformGenreStep)
                .next(transformAuthorStep)
                .next(transformBookStep)
                .next(transformCommentStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Job started");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Job finished");
                    }
                })
                .build();
    }

    @Bean
    public Step transformGenreStep(RepositoryItemReader<Genre> reader, RepositoryItemWriter<GenreDocument> writer,
                                   ItemProcessor<Genre, GenreDocument> itemProcessor) {
        return stepBuilderFactory.get("migrateGenre")
                .<Genre, GenreDocument>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step transformAuthorStep(RepositoryItemReader<Author> reader, RepositoryItemWriter<AuthorDocument> writer,
                                    ItemProcessor<Author, AuthorDocument> itemProcessor) {
        return stepBuilderFactory.get("migrateAuthor")
                .<Author, AuthorDocument>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step transformBookStep(RepositoryItemReader<Book> reader, RepositoryItemWriter<BookDocument> writer,
                                  ItemProcessor<Book, BookDocument> itemProcessor) {
        return stepBuilderFactory.get("migrateBook")
                .<Book, BookDocument>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step transformCommentStep(RepositoryItemReader<Comment> reader, RepositoryItemWriter<CommentDocument> writer,
                                     ItemProcessor<Comment, CommentDocument> itemProcessor) {
        return stepBuilderFactory.get("migrateComments")
                .<Comment, CommentDocument>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
}
