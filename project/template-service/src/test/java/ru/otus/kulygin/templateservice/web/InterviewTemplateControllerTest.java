package ru.otus.kulygin.templateservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.kulygin.templateservice.BaseTest;
import ru.otus.kulygin.templateservice.dto.pageable.InterviewTemplatePageableDto;
import ru.otus.kulygin.templateservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.templateservice.exception.InterviewTemplateDoesNotExistException;
import ru.otus.kulygin.templateservice.service.InterviewTemplateService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(InterviewTemplateController.class)
class InterviewTemplateControllerTest extends BaseTest {

    public static final String API = "/interview-template/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterviewTemplateService interviewTemplateService;

    @Test
    void shouldGetAllPageable() throws Exception {
        when(interviewTemplateService.findAll(getPageRequestP0PS10())).thenReturn(InterviewTemplatePageableDto.builder()
                .templates(getTemplatesDto())
                .build());

        mockMvc.perform(get(API + "?page=0&pageSize=10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAll() throws Exception {
        when(interviewTemplateService.findAll()).thenReturn(InterviewTemplatePageableDto.builder()
                .templates(getTemplatesDto())
                .build());

        mockMvc.perform(get(API))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSave() throws Exception {
        when(interviewTemplateService.save(getTemplatesDto().get(0))).thenReturn(getTemplatesDto().get(0));

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTemplatesDto().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotSaveInterviewTemplateDoesNotExistException() throws Exception {
        when(interviewTemplateService.save(getTemplatesDto().get(0))).thenThrow(InterviewTemplateDoesNotExistException.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTemplatesDto().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Interview template by id has not found"));
    }

    @Test
    void shouldGetById() throws Exception {
        when(interviewTemplateService.getById("1")).thenReturn(getTemplatesDto().get(0));

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetById() throws Exception {
        when(interviewTemplateService.getById("1")).thenThrow(InterviewTemplateDoesNotExistException.class);

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Interview template by id has not found"));
    }

    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteById() throws Exception {
        doThrow(RuntimeException.class).when(interviewTemplateService).deleteById("1");

        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("600"));
    }

    @Test
    void shouldAddCriteria() throws Exception {
        when(interviewTemplateService.addCriteria("1", getTemplatesDto().get(0).getCriterias().get(0)))
                .thenReturn(getTemplatesDto().get(0));

        mockMvc.perform(post(API + "1/criteria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTemplatesDto().get(0).getCriterias().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotAddCriteriaInterviewTemplateDoesNotExistException() throws Exception {
        when(interviewTemplateService.addCriteria("1", getTemplatesDto().get(0).getCriterias().get(0)))
                .thenThrow(InterviewTemplateDoesNotExistException.class);

        mockMvc.perform(post(API + "1/criteria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTemplatesDto().get(0).getCriterias().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Interview template by id has not found"));
    }

    @Test
    void shouldDeleteCriteria() throws Exception {
        when(interviewTemplateService.deleteCriteria("1", "2"))
                .thenReturn(getTemplatesDto().get(0));

        mockMvc.perform(delete(API + "1/criteria/2"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCriteriaInterviewTemplateDoesNotExistException() throws Exception {
        when(interviewTemplateService.deleteCriteria("1", "2"))
                .thenThrow(InterviewTemplateDoesNotExistException.class);

        mockMvc.perform(delete(API + "1/criteria/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Interview template by id has not found"));
    }

    @Test
    void shouldDeleteCriteriaInterviewTemplateCriteriaDoesNotExist() throws Exception {
        when(interviewTemplateService.deleteCriteria("1", "2"))
                .thenThrow(InterviewTemplateCriteriaDoesNotExist.class);

        mockMvc.perform(delete(API + "1/criteria/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Criteria by id has not found"));
    }

    @Test
    void shouldExistsByCriteriaId() throws Exception {
        when(interviewTemplateService.existByCriteriaId("1")).thenReturn(true);

        mockMvc.perform(get(API + "criteria/exists/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAllByCriteriaId() throws Exception {
        when(interviewTemplateService.findAllByCriteriaId("1")).thenReturn(getTemplatesDto());

        mockMvc.perform(get(API + "criteria/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSaveAll() throws Exception {
        when(interviewTemplateService.saveAll(getTemplatesDto())).thenReturn(getTemplatesDto());

        mockMvc.perform(post(API + "list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTemplatesDto())))
                .andExpect(status().isOk());
    }
}