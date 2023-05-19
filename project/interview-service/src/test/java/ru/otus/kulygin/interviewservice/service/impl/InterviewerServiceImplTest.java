package ru.otus.kulygin.interviewservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.interviewservice.feign.InterviewerServiceClient;
import ru.otus.kulygin.interviewservice.service.InterviewerService;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(classes = InterviewerServiceImpl.class)
class InterviewerServiceImplTest {

    @Autowired
    private InterviewerService interviewerService;

    @MockBean
    private InterviewerServiceClient interviewerServiceClient;

    @Test
    void shouldGetInterviewerById() {
        interviewerService.getInterviewerById("1");

        verify(interviewerServiceClient).getInterviewerById("1");
    }
}