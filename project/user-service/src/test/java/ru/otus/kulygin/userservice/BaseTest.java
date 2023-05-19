package ru.otus.kulygin.userservice;

import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.userservice.domain.Interviewer;
import ru.otus.kulygin.userservice.dto.InterviewerDto;
import ru.otus.kulygin.userservice.enumerations.UserRoles;

import java.util.List;


public class BaseTest {

    protected static Pageable getPageRequestP0PS10() {
        return PageRequest.of(0, 10);
    }

    protected static List<Interviewer> getUserList() {
        val interviewer = Interviewer.builder()
                .firstName("Джон")
                .lastName("Петрович")
                .username("johnyp")
                /* 123 */
                .password("$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci")
                .role(UserRoles.DEVELOPER.getRoleName())
                .positionType("Java Developer")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer2 = Interviewer.builder()
                .firstName("Василий")
                .lastName("Петров")
                .username("vasya.petrov")
                /* 456 */
                .password("$2y$10$7Ft9T51ViFT86a2E8nf/7uVe3b97gWhW5DNMr4zSmmkMFJeU7jg2a")
                .positionType("JavaScript Developer")
                .role(UserRoles.DEVELOPER.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer3 = Interviewer.builder()
                .firstName("Анна")
                .lastName("Светлова")
                .username("svetik")
                /* 789 */
                .password("$2y$10$60EoXkjJi/NqMzMIs8BltOaE5JkDCjJVSJNR9sLoMoy6giq5mR3za")
                .positionType("Scala Developer")
                .role(UserRoles.DEVELOPER.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer4 = Interviewer.builder()
                .firstName("Лиза")
                .lastName("Иванова")
                .username("lizzy")
                /* 777 */
                .password("$2y$10$Xf1ZPFEUyuZ2usd.hxl0ce79ur9p8OKVSQLk33yi1T8VqECHl4Jue")
                .positionType("HR")
                .role(UserRoles.HR.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        return List.of(interviewer, interviewer2, interviewer3, interviewer4);
    }

    protected static List<InterviewerDto> getUserListDto() {
        val interviewer = InterviewerDto.builder()
                .firstName("Джон")
                .lastName("Петрович")
                .username("johnyp")
                /* 123 */
                .password("$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci")
                .role(UserRoles.DEVELOPER.getRoleName())
                .positionType("Java Developer")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer2 = InterviewerDto.builder()
                .firstName("Василий")
                .lastName("Петров")
                .username("vasya.petrov")
                /* 456 */
                .password("$2y$10$7Ft9T51ViFT86a2E8nf/7uVe3b97gWhW5DNMr4zSmmkMFJeU7jg2a")
                .positionType("JavaScript Developer")
                .role(UserRoles.DEVELOPER.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer3 = InterviewerDto.builder()
                .firstName("Анна")
                .lastName("Светлова")
                .username("svetik")
                /* 789 */
                .password("$2y$10$60EoXkjJi/NqMzMIs8BltOaE5JkDCjJVSJNR9sLoMoy6giq5mR3za")
                .positionType("Scala Developer")
                .role(UserRoles.DEVELOPER.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer4 = InterviewerDto.builder()
                .firstName("Лиза")
                .lastName("Иванова")
                .username("lizzy")
                /* 777 */
                .password("$2y$10$Xf1ZPFEUyuZ2usd.hxl0ce79ur9p8OKVSQLk33yi1T8VqECHl4Jue")
                .positionType("HR")
                .role(UserRoles.HR.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        return List.of(interviewer, interviewer2, interviewer3, interviewer4);
    }
}
