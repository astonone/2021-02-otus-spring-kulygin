package ru.otus.kulygin.criteriaservice;

import lombok.val;
import org.apache.http.entity.ContentType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.criteriaservice.dto.InterviewTemplateCriteriaDto;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BaseTest {

    private static final String TEST_INPUT_FILE_NAME = "test-criterias.csv";

    protected static MultipartFile getMockCsvMultipartFile() {
        var classLoader = BaseTest.class.getClassLoader();

        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8);
        FileSystemResource testInputFileResource = new FileSystemResource(testInputFileName);
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(testInputFileResource.getFile().getName(),
                    testInputFileResource.getFile().getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), testInputFileResource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return multipartFile;
    }

    protected static List<InterviewTemplateCriteria> getCriterias() {
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

        return Arrays.asList(criteria1, criteria2, criteria3, criteria4, criteria5);
    }

    protected static List<InterviewTemplateCriteriaDto> getCriteriasDto() {
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

        return Arrays.asList(criteria1, criteria2, criteria3, criteria4, criteria5);
    }

    protected static Pageable getPageRequestP0PS10() {
        return PageRequest.of(0, 10);
    }
}
