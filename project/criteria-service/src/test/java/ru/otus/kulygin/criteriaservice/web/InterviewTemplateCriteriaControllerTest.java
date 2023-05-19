package ru.otus.kulygin.criteriaservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.kulygin.criteriaservice.BaseTest;
import ru.otus.kulygin.criteriaservice.dto.pageable.InterviewTemplateCriteriaPageableDto;
import ru.otus.kulygin.criteriaservice.exception.FileWritingException;
import ru.otus.kulygin.criteriaservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.criteriaservice.service.BatchService;
import ru.otus.kulygin.criteriaservice.service.InterviewTemplateCriteriaService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(InterviewTemplateCriteriaController.class)
class InterviewTemplateCriteriaControllerTest extends BaseTest {

    private static final String TEST_JSON = "src/test/resources/test-criterias.csv";
    public static final String API = "/interview-template-criteria/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterviewTemplateCriteriaService interviewTemplateCriteriaService;

    @MockBean
    private BatchService batchService;

    @Test
    void shouldGetAllPageable() throws Exception {
        when(interviewTemplateCriteriaService.findAll(getPageRequestP0PS10())).thenReturn(InterviewTemplateCriteriaPageableDto.builder()
                .interviewTemplateCriterias(getCriteriasDto())
                .build());

        mockMvc.perform(get(API + "?page=0&pageSize=10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSave() throws Exception {
        when(interviewTemplateCriteriaService.save(getCriteriasDto().get(0))).thenReturn(getCriteriasDto().get(0));

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getCriteriasDto().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotSaveCriteriaNotFound() throws Exception {
        when(interviewTemplateCriteriaService.save(getCriteriasDto().get(0))).thenThrow(InterviewTemplateCriteriaDoesNotExist.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getCriteriasDto().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Criteria by id has not found"));
    }

    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteById() throws Exception {
        doThrow(RuntimeException.class).when(interviewTemplateCriteriaService).deleteById("1");

        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("600"));
    }

    @Test
    void shouldImportCriterias() throws Exception {
        Path path = Paths.get(TEST_JSON);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));

        mockMvc.perform(multipart(API + "job/import-criterias")
                .file(uploadedFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Batch job successfully started"));

        verify(batchService).startImportCriteriasJob(uploadedFile);
    }

    @Test
    void shouldNotImportCriteriasFileWritingException() throws Exception {
        Path path = Paths.get(TEST_JSON);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));

        doThrow(FileWritingException.class).when(batchService).startImportCriteriasJob(uploadedFile);

        mockMvc.perform(multipart(API + "job/import-criterias")
                .file(uploadedFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("777"))
                .andExpect(jsonPath("$.message").value("Problem with storaging file for batch"));
    }

    @Test
    void shouldNotImportCriteriasJobInstanceAlreadyExistsException() throws Exception {
        Path path = Paths.get(TEST_JSON);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));

        doThrow(JobInstanceAlreadyExistsException.class).when(batchService).startImportCriteriasJob(uploadedFile);

        mockMvc.perform(multipart(API + "job/import-criterias")
                .file(uploadedFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("779"))
                .andExpect(jsonPath("$.message").value("Job instance already exists"));
    }

    @Test
    void shouldNotImportCriteriasNoSuchJobException() throws Exception {
        Path path = Paths.get(TEST_JSON);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));

        doThrow(NoSuchJobException.class).when(batchService).startImportCriteriasJob(uploadedFile);

        mockMvc.perform(multipart(API + "job/import-criterias")
                .file(uploadedFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("778"))
                .andExpect(jsonPath("$.message").value("Job for import criterias was not found"));
    }

    @Test
    void shouldNotImportCriteriasJobParametersInvalidException() throws Exception {
        Path path = Paths.get(TEST_JSON);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));

        doThrow(JobParametersInvalidException.class).when(batchService).startImportCriteriasJob(uploadedFile);

        mockMvc.perform(multipart(API + "job/import-criterias")
                .file(uploadedFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("780"))
                .andExpect(jsonPath("$.message").value("Invalid job parameters"));
    }

    @Test
    void shouldGetImportJobInfo() throws Exception {
        when(batchService.getLastJobExecutionInfo()).thenReturn("job info");

        mockMvc.perform(get(API + "job/import-criterias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("job info"));
    }

    @Test
    void shouldGetImportJobInfoNoSuchJobExecutionException() throws Exception {
        when(batchService.getLastJobExecutionInfo()).thenThrow(NoSuchJobExecutionException.class);

        mockMvc.perform(get(API + "job/import-criterias"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("781"))
                .andExpect(jsonPath("$.message").value("Job instance was not found"));
    }

    @Test
    void shouldExistById() throws Exception {
        when(interviewTemplateCriteriaService.existById("1")).thenReturn(true);

        mockMvc.perform(get(API + "exists/1"))
                .andExpect(status().isOk());
    }
}