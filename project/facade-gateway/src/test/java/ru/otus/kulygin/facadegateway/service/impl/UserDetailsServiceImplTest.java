package ru.otus.kulygin.facadegateway.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.facadegateway.service.UserService;
import ru.otus.kulygin.facadegateway.vo.InterviewerVO;
import ru.otus.kulygin.facadegateway.vo.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@SpringBootTest(classes = UserDetailsServiceImpl.class)
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private MappingService mappingService;

    @Test
    void shouldLoadUserByUsername() {
        val interviewerVO = InterviewerVO.builder()
                .username("user")
                .build();
        val user = User.builder()
                .username("user")
                .build();

        when(userService.getByUsername("user")).thenReturn(interviewerVO);
        when(mappingService.map(interviewerVO, User.class)).thenReturn(user);

        val result = userDetailsService.loadUserByUsername("user");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldNotLoadUserByUsername() {
        val interviewerVO = InterviewerVO.builder()
                .username("user")
                .build();
        val user = User.builder()
                .username("user")
                .build();

        when(userService.getByUsername("user")).thenReturn(interviewerVO);
        when(mappingService.map(interviewerVO, User.class)).thenReturn(null);

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("user"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}