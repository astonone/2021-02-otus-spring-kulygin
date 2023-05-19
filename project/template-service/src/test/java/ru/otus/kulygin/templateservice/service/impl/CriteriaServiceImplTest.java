package ru.otus.kulygin.templateservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.templateservice.feign.CriteriaServiceClient;
import ru.otus.kulygin.templateservice.service.CriteriaService;
import ru.otus.kulygin.templateservice.service.impl.CriteriaServiceImpl;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(classes = CriteriaServiceImpl.class)
class CriteriaServiceImplTest {

    @Autowired
    private CriteriaService criteriaService;

    @MockBean
    private CriteriaServiceClient criteriaServiceClient;

    @Test
    void shouldExistsCriteriaById() {
        criteriaService.existsCriteriaById("1");

        verify(criteriaServiceClient).existsCriteriaById("1");
    }
}