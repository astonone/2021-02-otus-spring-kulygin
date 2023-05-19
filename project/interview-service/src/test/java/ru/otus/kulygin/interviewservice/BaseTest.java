package ru.otus.kulygin.interviewservice;

import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.interviewservice.domain.*;
import ru.otus.kulygin.interviewservice.dto.*;
import ru.otus.kulygin.interviewservice.enumerations.DecisionStatus;
import ru.otus.kulygin.interviewservice.enumerations.InterviewStatus;

import java.time.LocalDateTime;
import java.util.List;

public class BaseTest {

    protected static Pageable getPageRequestP0PS10() {
        return PageRequest.of(0, 10);
    }

    protected static List<Interview> getInterviews() {
        val interviewer = Interviewer.builder()
                .id("1")
                .firstName("Джон")
                .lastName("Петрович")
                .username("johnyp")
                .password("$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci")
                .role("ROLE_DEVELOPER")
                .positionType("Java Developer")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val candidate = Candidate.builder()
                .id("2")
                .firstName("Ирина")
                .lastName("Кулыгина")
                .claimingPosition("Java Developer")
                .interviewerComment("Должен быть толковый джун")
                .build();

        val criteria1 = InterviewTemplateCriteria.builder()
                .id("1")
                .name("Знание основных типов данных, переменных, операторов, циклов, массивов")
                .positionType("Java Developer")
                .build();

        val criteria2 = InterviewTemplateCriteria.builder()
                .id("2")
                .name("Назовите принципы ООП и расскажите о каждом")
                .positionType("Java Developer")
                .build();

        val criteria3 = InterviewTemplateCriteria.builder()
                .name("Какие модификации уровня доступа вы знаете, расскажите про каждый из них")
                .id("3")
                .positionType("Java Developer")
                .build();

        val criteria4 = InterviewTemplateCriteria.builder()
                .id("4")
                .name("Что такое сигнатура метода?")
                .positionType("Java Developer")
                .build();

        val criteria5 = InterviewTemplateCriteria.builder()
                .id("5")
                .name("Какие методы называются перегруженными?")
                .positionType("Java Developer")
                .build();

        val criterias = List.of(criteria1, criteria2, criteria3, criteria4, criteria5);

        val template = InterviewTemplate.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(criterias)
                .build();

        val interview = Interview.builder()
                .id("1")
                .interviewer(interviewer)
                .candidate(candidate)
                .interviewTemplate(template)
                .desiredSalary("2000 EUR")
                .interviewDateTime(LocalDateTime.of(2021, 9, 20, 15, 30))
                .interviewStatus(InterviewStatus.PLANNED)
                .decisionStatus(DecisionStatus.NOT_APPLICABLE)
                .build();

        return List.of(interview);
    }

    protected static List<InterviewDto> getInterviewsDto() {
        val interviewer = InterviewerDto.builder()
                .id("1")
                .firstName("Джон")
                .lastName("Петрович")
                .username("johnyp")
                .password("$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci")
                .role("ROLE_DEVELOPER")
                .positionType("Java Developer")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val candidate = CandidateDto.builder()
                .id("2")
                .firstName("Ирина")
                .lastName("Кулыгина")
                .claimingPosition("Java Developer")
                .interviewerComment("Должен быть толковый джун")
                .build();

        val criteria1 = InterviewTemplateCriteriaDto.builder()
                .id("1")
                .name("Знание основных типов данных, переменных, операторов, циклов, массивов")
                .positionType("Java Developer")
                .build();

        val criteria2 = InterviewTemplateCriteriaDto.builder()
                .id("2")
                .name("Назовите принципы ООП и расскажите о каждом")
                .positionType("Java Developer")
                .build();

        val criteria3 = InterviewTemplateCriteriaDto.builder()
                .name("Какие модификации уровня доступа вы знаете, расскажите про каждый из них")
                .id("3")
                .positionType("Java Developer")
                .build();

        val criteria4 = InterviewTemplateCriteriaDto.builder()
                .id("4")
                .name("Что такое сигнатура метода?")
                .positionType("Java Developer")
                .build();

        val criteria5 = InterviewTemplateCriteriaDto.builder()
                .id("5")
                .name("Какие методы называются перегруженными?")
                .positionType("Java Developer")
                .build();

        val criterias = List.of(criteria1, criteria2, criteria3, criteria4, criteria5);

        val template = InterviewTemplateDto.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(criterias)
                .build();

        val interview = InterviewDto.builder()
                .id("1")
                .interviewer(interviewer)
                .candidate(candidate)
                .interviewTemplate(template)
                .desiredSalary("2000 EUR")
                .interviewDateTime(LocalDateTime.of(2021, 9, 20, 15, 30))
                .interviewStatus(InterviewStatus.PLANNED.getCode())
                .decisionStatus(DecisionStatus.NOT_APPLICABLE.getCode())
                .build();

        return List.of(interview);
    }
}
