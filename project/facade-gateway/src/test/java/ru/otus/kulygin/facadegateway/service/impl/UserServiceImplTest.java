package ru.otus.kulygin.facadegateway.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.facadegateway.feign.UserServiceClient;
import ru.otus.kulygin.facadegateway.service.UserService;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserServiceClient userServiceClient;

    @Test
    void shouldGetByUsername() {
        userService.getByUsername("user");

        verify(userServiceClient).getByUsername("user");
    }
}