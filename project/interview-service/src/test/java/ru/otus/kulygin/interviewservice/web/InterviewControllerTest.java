package ru.otus.kulygin.interviewservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.kulygin.interviewservice.BaseTest;
import ru.otus.kulygin.interviewservice.dto.InterviewDto;
import ru.otus.kulygin.interviewservice.dto.pageable.InterviewPageableDto;
import ru.otus.kulygin.interviewservice.enumerations.InterviewStatus;
import ru.otus.kulygin.interviewservice.exception.InterviewDecisionException;
import ru.otus.kulygin.interviewservice.exception.InterviewDoesNotExistException;
import ru.otus.kulygin.interviewservice.exception.InterviewStatusException;
import ru.otus.kulygin.interviewservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.interviewservice.service.InterviewService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(InterviewController.class)
class InterviewControllerTest extends BaseTest {

    public static final String API = "/interview/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterviewService interviewService;

    @Test
    void shouldGetAllPageable() throws Exception {
        when(interviewService.findAll(getPageRequestP0PS10())).thenReturn(InterviewPageableDto.builder()
                .interviews(getInterviewsDto())
                .build());

        mockMvc.perform(get(API + "?page=0&pageSize=10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAll() throws Exception {
        when(interviewService.findAll()).thenReturn(InterviewPageableDto.builder()
                .interviews(getInterviewsDto())
                .build());

        mockMvc.perform(get(API))
                .andExpect(status().isOk());
    }

    @Test
    void ShouldGetAllByStatus() throws Exception {
        when(interviewService.findAllByInterviewStatus(getPageRequestP0PS10(),
                InterviewStatus.PLANNED.getCode()))
                .thenReturn(InterviewPageableDto.builder()
                        .interviews(getInterviewsDto())
                        .build());

        mockMvc.perform(get(API + "?page=0&pageSize=10&status=PLANNED"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetById() throws Exception {
        when(interviewService.getById("1")).thenReturn(getInterviewsDto().get(0));

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetById() throws Exception {
        when(interviewService.getById("1")).thenThrow(InterviewDoesNotExistException.class);

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("501"))
                .andExpect(jsonPath("$.message").value("Interview by id has not found"));
    }

    @Test
    void shouldSave() throws Exception {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setInterviewDateTime(null);
        when(interviewService.save(interviewDto)).thenReturn(interviewDto);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(interviewDto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotSaveInterviewDoesNotExistException() throws Exception {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setInterviewDateTime(null);
        when(interviewService.save(interviewDto)).thenThrow(InterviewDoesNotExistException.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(interviewDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("501"))
                .andExpect(jsonPath("$.message").value("Interview by id has not found"));
    }

    @Test
    void shouldNotSaveInterviewStatusException() throws Exception {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setInterviewDateTime(null);
        when(interviewService.save(interviewDto)).thenThrow(InterviewStatusException.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(interviewDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("700"))
                .andExpect(jsonPath("$.message").value("New interview must have status only: PLANNED"));
    }

    @Test
    void shouldNotSaveInterviewDecisionException() throws Exception {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setInterviewDateTime(null);
        when(interviewService.save(interviewDto)).thenThrow(InterviewDecisionException.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(interviewDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("800"))
                .andExpect(jsonPath("$.message").value("New interview must have decision only: NOT_APPLICABLE"));
    }

    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCriteria() throws Exception {
        when(interviewService.updateCriteria("1", "1", 5)).thenReturn(getInterviewsDto().get(0));

        mockMvc.perform(post(API + "1/criteria/1/?mark=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdateCriteriaInterviewDoesNotExistException() throws Exception {
        when(interviewService.updateCriteria("1", "1", 5))
                .thenThrow(InterviewDoesNotExistException.class);

        mockMvc.perform(post(API + "1/criteria/1/?mark=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("501"))
                .andExpect(jsonPath("$.message").value("Interview by id has not found"));
    }

    @Test
    void shouldNotUpdateCriteriaInterviewTemplateCriteriaDoesNotExist() throws Exception {
        when(interviewService.updateCriteria("1", "1", 5))
                .thenThrow(InterviewTemplateCriteriaDoesNotExist.class);

        mockMvc.perform(post(API + "1/criteria/1/?mark=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Criteria by id has not found"));
    }

    @Test
    void shouldUpdateCriteriaComment() throws Exception {
        when(interviewService.updateCriteriaComment("1", "1", getInterviewsDto().get(0)
                .getInterviewTemplate()
                .getCriterias().get(0))).thenReturn(getInterviewsDto().get(0));

        mockMvc.perform(post(API + "1/criteria/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getInterviewsDto().get(0)
                        .getInterviewTemplate()
                        .getCriterias().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCriteriaCommentInterviewDoesNotExistException() throws Exception {
        when(interviewService.updateCriteriaComment("1", "1", getInterviewsDto().get(0)
                .getInterviewTemplate()
                .getCriterias().get(0))).thenThrow(InterviewDoesNotExistException.class);

        mockMvc.perform(post(API + "1/criteria/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getInterviewsDto().get(0)
                        .getInterviewTemplate()
                        .getCriterias().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("501"))
                .andExpect(jsonPath("$.message").value("Interview by id has not found"));
    }

    @Test
    void shouldUpdateCriteriaCommentInterviewTemplateCriteriaDoesNotExist() throws Exception {
        when(interviewService.updateCriteriaComment("1", "1", getInterviewsDto().get(0)
                .getInterviewTemplate()
                .getCriterias().get(0))).thenThrow(InterviewTemplateCriteriaDoesNotExist.class);

        mockMvc.perform(post(API + "1/criteria/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getInterviewsDto().get(0)
                        .getInterviewTemplate()
                        .getCriterias().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Criteria by id has not found"));
    }

    @Test
    void shouldExistByCandidateId() throws Exception {
        when(interviewService.existsByCandidateId("1")).thenReturn(true);

        mockMvc.perform(get(API + "candidate/exist/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAllByCandidateId() throws Exception {
        when(interviewService.findAllByCandidateId("1")).thenReturn(getInterviewsDto());

        mockMvc.perform(get(API + "candidate/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldExistByInterviewerId() throws Exception {
        when(interviewService.existsByInterviewerId("1")).thenReturn(true);

        mockMvc.perform(get(API + "interviewer/exist/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldExistByTemplateId() throws Exception {
        when(interviewService.existsByInterviewTemplateId("1")).thenReturn(true);

        mockMvc.perform(get(API + "template/exist/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAllByInterviewerId() throws Exception {
        when(interviewService.findAllByInterviewerId("1")).thenReturn(getInterviewsDto());

        mockMvc.perform(get(API + "candidate/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAllByTemplateId() throws Exception {
        when(interviewService.findAllByInterviewTemplateIdAndInterviewStatus("1")).thenReturn(getInterviewsDto());

        mockMvc.perform(get(API + "template/1/planned"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAllByCriteriaId() throws Exception {
        when(interviewService.findAllPlannedByCriteriaId("1")).thenReturn(getInterviewsDto());

        mockMvc.perform(get(API + "criteria/1/planned"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSaveAll() throws Exception {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setInterviewDateTime(null);
        when(interviewService.saveAll(List.of(interviewDto))).thenReturn(getInterviewsDto());

        mockMvc.perform(post(API + "list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(List.of(interviewDto))))
                .andExpect(status().isOk());
    }
}