package ru.otus.kulygin.interviewservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.interviewservice.feign.TemplateServiceClient;
import ru.otus.kulygin.interviewservice.service.TemplateService;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(classes = TemplateServiceImpl.class)
class TemplateServiceImplTest {

    @Autowired
    private TemplateService templateService;

    @MockBean
    private TemplateServiceClient templateServiceClient;

    @Test
    void shouldGetInterviewTemplateById() {
        templateService.getInterviewTemplateById("1");

        verify(templateServiceClient).getInterviewTemplateById("1");
    }
}