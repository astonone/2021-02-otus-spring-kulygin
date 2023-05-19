package ru.otus.kulygin.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.kulygin.dto.UserDto;
import ru.otus.kulygin.exception.UserDoesNotExistException;
import ru.otus.kulygin.web.config.SpringSecurityWebTestConfig;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SpringSecurityWebTestConfig.class)
@DisplayName(value = "UserController should ")
@WebMvcTest(UserController.class)
class UserControllerTest extends BaseControllerTest {

    private static final String USER_API = "/api/user/";

    @Test
    @DisplayName(value = "remove user by id")
    @WithMockUser(username = "astonone")
    public void shouldRemoveGenreById() throws Exception {
        val user = UserDto.builder()
                .id("1")
                .build();

        mockMvc.perform(delete(USER_API + user.getId()))
                .andExpect(status().isOk());

        verify(userService).deleteById(user.getId());
    }

    @Test
    @DisplayName(value = "return not remove user by id")
    @WithMockUser(username = "astonone")
    public void shouldNotRemoveGenreById() throws Exception {
        val user = UserDto.builder()
                .id("2")
                .build();

        doThrow(UserDoesNotExistException.class).when(userService).deleteById(user.getId());

        mockMvc.perform(delete(USER_API + user.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName(value = "create new genre")
    public void shouldCreateGenre() throws Exception {
        val userDto = UserDto.builder()
                .username("1")
                .password("2")
                .build();

        val userSaved = UserDto.builder()
                .id("1")
                .username("1")
                .password("hash")
                .build();

        when(userService.save(userDto)).thenReturn(userSaved);

        mockMvc.perform(post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("1"))
                .andExpect(jsonPath("$.password").value("hash"));
    }

    @Test
    @DisplayName(value = "update existed genre")
    public void shouldUpdateGenre() throws Exception {
        val userDto = UserDto.builder()
                .id("1")
                .username("1")
                .password("2")
                .build();

        val userSaved = UserDto.builder()
                .id("1")
                .username("1")
                .password("hash")
                .build();

        when(userService.save(userDto)).thenReturn(userSaved);

        mockMvc.perform(post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("1"))
                .andExpect(jsonPath("$.password").value("hash"));
    }

    @Test
    @DisplayName(value = "not update existed genre")
    public void shouldNotUpdateGenre() throws Exception {
        val userDto = UserDto.builder()
                .id("1")
                .username("1")
                .password("2")
                .build();

        when(userService.save(userDto)).thenThrow(UserDoesNotExistException.class);

        mockMvc.perform(post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("5"))
                .andExpect(jsonPath("$.message").value("User by id has not found"));
    }

    @Test
    @DisplayName(value = "user login")
    public void shouldUserLogin() throws Exception {
        val userDto = UserDto.builder()
                .id("1")
                .username("1")
                .password("2")
                .build();

        when(userService.login(userDto)).thenReturn(true);

        mockMvc.perform(post(USER_API + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "not user login")
    public void shouldNotUserLogin() throws Exception {
        val userDto = UserDto.builder()
                .id("1")
                .username("1")
                .password("2")
                .build();

        when(userService.login(userDto)).thenReturn(false);

        mockMvc.perform(post(USER_API + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName(value = "user authenticate")
    @WithMockUser(username = "astonone")
    public void shouldUserAuth() throws Exception {
        val userDto = UserDto.builder()
                .id("1")
                .username("1")
                .password("hash")
                .build();

        when(userService.getUserByUsername(any(HttpServletRequest.class))).thenReturn(userDto);

        mockMvc.perform(post(USER_API + "authenticate"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("1"))
                .andExpect(jsonPath("$.password").value("hash"));
    }

}