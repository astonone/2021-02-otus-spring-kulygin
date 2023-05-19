package ru.otus.kulygin.candidateservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.kulygin.candidateservice.BaseTest;
import ru.otus.kulygin.candidateservice.dto.pageable.CandidatePageableDto;
import ru.otus.kulygin.candidateservice.exception.CandidateDoesNotExistException;
import ru.otus.kulygin.candidateservice.exception.FileWritingException;
import ru.otus.kulygin.candidateservice.exception.WrongCvFileFormatException;
import ru.otus.kulygin.candidateservice.service.CandidateService;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CandidateController.class)
class CandidateControllerTest extends BaseTest {

    private static final String TEST_CANDIDATE_JSON = "src/test/resources/candidate.json";
    private static final String TEST_CANDIDATE_CV_FILE = "src/test/resources/cvExample.pdf";
    public static final String API = "/candidate/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService candidateService;

    @Test
    void shouldGetAllPageable() throws Exception {
        when(candidateService.findAll(getPageRequestP0PS10())).thenReturn(CandidatePageableDto.builder()
                .candidates(getCandidatesDto())
                .build());

        mockMvc.perform(get(API + "?page=0&pageSize=10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAll() throws Exception {
        when(candidateService.findAll()).thenReturn(CandidatePageableDto.builder()
                .candidates(getCandidatesDto())
                .build());

        mockMvc.perform(get(API))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSave() throws Exception {
        FileInputStream candidateJson = new FileInputStream(TEST_CANDIDATE_JSON);
        MockMultipartFile candidate = new MockMultipartFile("candidateDto", "", "application/json", candidateJson);
        Path path = Paths.get(TEST_CANDIDATE_CV_FILE);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));

        mockMvc.perform(multipart(API)
                .file(uploadedFile)
                .file(candidate))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotSaveCandidateNotFound() throws Exception {
        FileInputStream candidateJson = new FileInputStream(TEST_CANDIDATE_JSON);
        MockMultipartFile candidate = new MockMultipartFile("candidateDto", "", "application/json", candidateJson);
        Path path = Paths.get(TEST_CANDIDATE_CV_FILE);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));
        when(candidateService.save(any(), any())).thenThrow(CandidateDoesNotExistException.class);

        mockMvc.perform(multipart(API)
                .file(uploadedFile)
                .file(candidate))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("300"))
                .andExpect(jsonPath("$.message").value("Candidate by id has not found"));
    }

    @Test
    void shouldNotSaveFileWritingError() throws Exception {
        FileInputStream candidateJson = new FileInputStream(TEST_CANDIDATE_JSON);
        MockMultipartFile candidate = new MockMultipartFile("candidateDto", "", "application/json", candidateJson);
        Path path = Paths.get(TEST_CANDIDATE_CV_FILE);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));
        when(candidateService.save(any(), any())).thenThrow(FileWritingException.class);

        mockMvc.perform(multipart(API)
                .file(uploadedFile)
                .file(candidate))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("301"))
                .andExpect(jsonPath("$.message").value("Error via file writing"));
    }

    @Test
    void shouldNotSaveFileFormatError() throws Exception {
        FileInputStream candidateJson = new FileInputStream(TEST_CANDIDATE_JSON);
        MockMultipartFile candidate = new MockMultipartFile("candidateDto", "", "application/json", candidateJson);
        Path path = Paths.get(TEST_CANDIDATE_CV_FILE);
        MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "cvExample.pdf", "text/plain", Files.readAllBytes(path));
        when(candidateService.save(any(), any())).thenThrow(WrongCvFileFormatException.class);

        mockMvc.perform(multipart(API)
                .file(uploadedFile)
                .file(candidate))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Incorrect file format! Should be only PDF"));
    }

    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteById() throws Exception {
        doThrow(RuntimeException.class).when(candidateService).deleteById("1");

        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("600"));
    }

    @Test
    void shouldGetById() throws Exception {
        when(candidateService.getById("1")).thenReturn(getCandidatesDto().get(0));

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetById() throws Exception {
        when(candidateService.getById("1")).thenThrow(CandidateDoesNotExistException.class);

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("300"))
                .andExpect(jsonPath("$.message").value("Candidate by id has not found"));
    }
}