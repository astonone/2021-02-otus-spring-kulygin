package ru.otus.kulygin.userservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.kulygin.userservice.BaseTest;
import ru.otus.kulygin.userservice.dto.InterviewerDto;
import ru.otus.kulygin.userservice.dto.pageable.InterviewerPageableDto;
import ru.otus.kulygin.userservice.exception.InterviewerDoesNotExistException;
import ru.otus.kulygin.userservice.exception.SecretKeyException;
import ru.otus.kulygin.userservice.exception.UsernameAlreadyExistException;
import ru.otus.kulygin.userservice.service.InterviewerService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(InterviewerController.class)
class InterviewerControllerTest extends BaseTest {

    public static final String API = "/interviewer/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterviewerService interviewerService;

    @Test
    void shouldGetByUsername() throws Exception {
        when(interviewerService.findByUsername("johnyp")).thenReturn(getUserList().get(0));

        mockMvc.perform(get(API + "username/johnyp"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldLogin() throws Exception {
        final InterviewerDto interviewerDto = InterviewerDto.builder()
                .username("user")
                .password("password")
                .build();

        when(interviewerService.login(interviewerDto)).thenReturn("1");

        mockMvc.perform(post(API + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(interviewerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void shouldNotLogin() throws Exception {
        final InterviewerDto interviewerDto = InterviewerDto.builder()
                .username("user")
                .password("password")
                .build();

        when(interviewerService.login(interviewerDto)).thenReturn(null);

        mockMvc.perform(post(API + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(interviewerDto)))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void shouldGetById() throws Exception {
        when(interviewerService.getById("1")).thenReturn(getUserListDto().get(0));

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetById() throws Exception {
        when(interviewerService.getById("1")).thenThrow(InterviewerDoesNotExistException.class);

        mockMvc.perform(get(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("100"))
                .andExpect(jsonPath("$.message").value("Interviewer by id has not found"));
    }

    @Test
    void shouldGetAllPageable() throws Exception {
        when(interviewerService.findAll(getPageRequestP0PS10())).thenReturn(InterviewerPageableDto.builder()
                .interviewers(getUserListDto())
                .build());

        mockMvc.perform(get(API + "?page=0&pageSize=10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAll() throws Exception {
        when(interviewerService.findAll()).thenReturn(InterviewerPageableDto.builder()
                .interviewers(getUserListDto())
                .build());

        mockMvc.perform(get(API))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSave() throws Exception {
        when(interviewerService.save(getUserListDto().get(0))).thenReturn(getUserListDto().get(0));

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getUserListDto().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotSaveUserNotFound() throws Exception {
        when(interviewerService.save(getUserListDto().get(0))).thenThrow(InterviewerDoesNotExistException.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getUserListDto().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("100"))
                .andExpect(jsonPath("$.message").value("Interviewer by id has not found"));
    }

    @Test
    void shouldNotSaveUsernameExists() throws Exception {
        when(interviewerService.save(getUserListDto().get(0))).thenThrow(UsernameAlreadyExistException.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getUserListDto().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("102"))
                .andExpect(jsonPath("$.message").value("User with this username already exists"));
    }

    @Test
    void shouldNotSaveSecretKeyException() throws Exception {
        when(interviewerService.save(getUserListDto().get(0))).thenThrow(SecretKeyException.class);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getUserListDto().get(0))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("110"))
                .andExpect(jsonPath("$.message").value("Incorrect secret key"));
    }

    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteById() throws Exception {
        doThrow(RuntimeException.class).when(interviewerService).deleteById("1");

        mockMvc.perform(delete(API + "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("600"));
    }
}