package ru.otus.kulygin.templateservice;

import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.otus.kulygin.templateservice.domain.InterviewTemplate;
import ru.otus.kulygin.templateservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateDto;

import java.util.List;

public class BaseTest {

    protected static Pageable getPageRequestP0PS10() {
        return PageRequest.of(0, 10);
    }

    protected static List<InterviewTemplate> getTemplates() {
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

        return List.of(template);
    }

    protected static List<InterviewTemplateDto> getTemplatesDto() {
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

        return List.of(template);
    }
}
