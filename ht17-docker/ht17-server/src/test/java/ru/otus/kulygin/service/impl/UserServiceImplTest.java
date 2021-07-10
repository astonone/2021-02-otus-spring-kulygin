package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.kulygin.domain.User;
import ru.otus.kulygin.dto.UserDto;
import ru.otus.kulygin.exception.UserDoesNotExistException;
import ru.otus.kulygin.repository.UserRepository;
import ru.otus.kulygin.service.UserService;
import ru.otus.kulygin.service.impl.UserServiceImpl;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserServiceImpl.class)
@DisplayName(value = "UserServiceImpl should ")
class UserServiceImplTest {

    public static final String NOT_EXISTED_USER_ID = "2";

    public static final String FOR_INSERT_USER_ID = "1";
    public static final String FOR_INSERT_USERNAME = "astonone";
    public static final String FOR_INSERT_PASSWORD = "222";

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MappingService mappingService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("add user to database")
    public void shouldInsertAuthor() {
        val userDto = UserDto.builder()
                .username(FOR_INSERT_USERNAME)
                .password(FOR_INSERT_PASSWORD)
                .build();

        userService.save(userDto);

        verify(passwordEncoder).encode(FOR_INSERT_PASSWORD);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("return expected user by id")
    public void shouldGetAuthorById() {
        val user = Optional.of(User.builder()
                .id(FOR_INSERT_USER_ID)
                .build());

        when(userRepository.findById(FOR_INSERT_USER_ID)).thenReturn(user);

        userService.getById(user.get().getId());

        verify(userRepository).findById(FOR_INSERT_USER_ID);
        verify(mappingService).map(user.get(), UserDto.class);
    }

    @Test
    @DisplayName("not return expected user by id because user does not exist")
    void shouldNotReturnExpectedAuthorById() {
        when(userRepository.findById(NOT_EXISTED_USER_ID)).thenReturn(Optional.empty());

        assertThat(userService.getById(NOT_EXISTED_USER_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("remove user by id")
    void shouldCorrectDeleteAuthorById() {
        when(userRepository.existsById(FOR_INSERT_USER_ID)).thenReturn(true);

        userRepository.deleteById(FOR_INSERT_USER_ID);

        verify(userRepository).deleteById(FOR_INSERT_USER_ID);
    }

    @Test
    @DisplayName("not remove user by id because user does not exist")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorDoesNotExist() {
        when(userRepository.existsById(FOR_INSERT_USER_ID)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteById(NOT_EXISTED_USER_ID))
                .isInstanceOf(UserDoesNotExistException.class);
    }

    @Test
    @DisplayName("login")
    void shouldUserLogin() {
        val userDto = UserDto.builder()
                .username(FOR_INSERT_USERNAME)
                .password(FOR_INSERT_PASSWORD)
                .build();

        val user = User.builder()
                .username(FOR_INSERT_USERNAME)
                .password("hash")
                .build();

        when(userRepository.findByUsername(FOR_INSERT_USERNAME)).thenReturn(user);
        when(passwordEncoder.matches(userDto.getPassword(), user.getPassword())).thenReturn(true);

        val login = userService.login(userDto);

        assertThat(login).isTrue();
    }

    @Test
    @DisplayName("not login")
    void shouldNotUserLogin() {
        val userDto = UserDto.builder()
                .username(FOR_INSERT_USERNAME)
                .password(FOR_INSERT_PASSWORD)
                .build();

        val user = User.builder()
                .username(FOR_INSERT_USERNAME)
                .password("hash")
                .build();

        when(userRepository.findByUsername(FOR_INSERT_USERNAME)).thenReturn(user);
        when(passwordEncoder.matches(userDto.getPassword(), user.getPassword())).thenReturn(false);

        val login = userService.login(userDto);

        assertThat(login).isFalse();
    }

    @Test
    @DisplayName("get user by username from request's headers")
    void shouldGetUserByUsername() {
        val userDto = UserDto.builder()
                .username(FOR_INSERT_USERNAME)
                .password(FOR_INSERT_PASSWORD)
                .build();

        val user = User.builder()
                .username(FOR_INSERT_USERNAME)
                .password("hash")
                .build();

        when(userRepository.findByUsername(FOR_INSERT_USERNAME)).thenReturn(user);
        when(mappingService.map(user, UserDto.class)).thenReturn(userDto);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic YXN0b25vbmU6MTIz");

        val result = userService.getUserByUsername(request);

        assertThat(result).isEqualTo(userDto);
    }

}