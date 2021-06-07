package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.otus.kulygin.domain.User;
import ru.otus.kulygin.repository.UserRepository;
import ru.otus.kulygin.service.impl.UserDetailsServiceImpl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserDetailsServiceImpl.class)
@DisplayName(value = "UserDetailsServiceImpl should ")
class UserDetailsServiceImplTest {

    private static final String USERNAME = "aston";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName(value = "load existed user by username")
    public void shouldLoadUserByUsername() {
        val existedUser = User.builder().username(USERNAME).build();
        when(userRepository.findByUsername(USERNAME)).thenReturn(existedUser);

        val aston = userDetailsService.loadUserByUsername(USERNAME);

        assertThat(aston).isEqualTo(existedUser);
    }

    @Test
    @DisplayName(value = "not load user by username")
    public void shouldNotLoadUserByUsername() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(USERNAME))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with username: " + USERNAME + " was not found!");
    }

}