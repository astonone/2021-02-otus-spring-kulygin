package ru.otus.kulygin.criteriaservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.criteriaservice.feign.TemplateServiceClient;
import ru.otus.kulygin.criteriaservice.service.TemplateService;
import ru.otus.kulygin.criteriaservice.vo.InterviewTemplateVO;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(classes = TemplateServiceImpl.class)
class TemplateServiceImplTest {

    @Autowired
    private TemplateService templateService;

    @MockBean
    private TemplateServiceClient templateServiceClient;

    @Test
    void shouldExistsTemplateByCriteriaId() {
        templateService.existsTemplateByCriteriaId("1");

        verify(templateServiceClient).existsTemplateByCriteriaId("1");
    }

    @Test
    void shouldFindAllByCriteriaId() {
        templateServiceClient.findAllByCriteriaId("1");

        verify(templateServiceClient).findAllByCriteriaId("1");
    }

    @Test
    void shouldSaveAll() {
        final ArrayList<InterviewTemplateVO> interviews = new ArrayList<>();

        templateService.saveAll(interviews);

        verify(templateServiceClient).saveAll(interviews);
    }
}