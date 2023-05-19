package ru.otus.kulygin.web.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.otus.kulygin.domain.User;
import ru.otus.kulygin.enumerations.UserRoles;

import java.util.Collections;

@TestConfiguration
public class SpringSecurityWebTestConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        User basicUser = User.builder()
                .id("1")
                .username("astonone")
                .password("123")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .role(UserRoles.USER.getRoleName())
                .build();

        return new InMemoryUserDetailsManager(Collections.singletonList(basicUser));
    }

}
