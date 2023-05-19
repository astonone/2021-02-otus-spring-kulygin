package ru.otus.kulygin.userservice.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.kulygin.userservice.domain.Interviewer;
import ru.otus.kulygin.userservice.enumerations.UserRoles;
import ru.otus.kulygin.userservice.repository.InterviewerRepository;

import java.util.Arrays;

@ChangeLog(order = "001")
public class Changelog001 {

    @ChangeSet(order = "001", id = "2021-09-04--001-insert-interviewers--vkulygin", author = "viktor.kulygin")
    public void insertInterviewers(InterviewerRepository interviewerRepository, PasswordEncoder passwordEncoder) {

        val interviewer = Interviewer.builder()
                .id("1")
                .firstName("Джон")
                .lastName("Петрович")
                .username("johnyp")
                .password(passwordEncoder.encode("123"))
                .role(UserRoles.DEVELOPER.getRoleName())
                .positionType("Java Developer")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer2 = Interviewer.builder()
                .id("2")
                .firstName("Василий")
                .lastName("Петров")
                .username("vasya.petrov")
                .password(passwordEncoder.encode("456"))
                .positionType("JavaScript Developer")
                .role(UserRoles.DEVELOPER.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer3 = Interviewer.builder()
                .id("3")
                .firstName("Анна")
                .lastName("Светлова")
                .username("svetik")
                .password(passwordEncoder.encode("789"))
                .positionType("Scala Developer")
                .role(UserRoles.DEVELOPER.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val interviewer4 = Interviewer.builder()
                .id("4")
                .firstName("Лиза")
                .lastName("Иванова")
                .username("lizzy")
                .password(passwordEncoder.encode("777"))
                .positionType("HR")
                .role(UserRoles.HR.getRoleName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        interviewerRepository.saveAll(Arrays.asList(interviewer, interviewer2, interviewer3, interviewer4));
    }

}
