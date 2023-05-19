package ru.otus.kulygin.interviewservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.interviewservice.feign.CandidateServiceClient;
import ru.otus.kulygin.interviewservice.service.CandidateService;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(classes = CandidateServiceImpl.class)
class CandidateServiceImplTest {

    @Autowired
    private CandidateService candidateService;

    @MockBean
    private CandidateServiceClient candidateServiceClient;

    @Test
    void shouldGetCandidateById() {
        candidateService.getCandidateById("1");

        verify(candidateServiceClient).getCandidateById("1");
    }
}