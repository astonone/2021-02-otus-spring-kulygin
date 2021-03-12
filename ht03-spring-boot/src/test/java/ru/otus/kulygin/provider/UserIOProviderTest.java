package ru.otus.kulygin.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UserIOProvider.class)
@DisplayName(value = "UserIOProvider should ")
class UserIOProviderTest {

    @Autowired
    private UserIOProvider userIOProvider;

    @Test
    @DisplayName(value = "get input")
    void shouldGetInput() {
        assertThat(userIOProvider.getInput()).isEqualTo(System.in);
    }

    @Test
    @DisplayName(value = "get output")
    void shouldGetOutput() {
        assertThat(userIOProvider.getOutput()).isEqualTo(System.out);
    }

}