package ru.otus.kulygin.userservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.userservice.feign.InterviewServiceClient;
import ru.otus.kulygin.userservice.service.InterviewService;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(classes = InterviewServiceImpl.class)
class InterviewServiceImplTest {

    @Autowired
    private InterviewService interviewService;

    @MockBean
    private InterviewServiceClient interviewServiceClient;

    @Test
    void shouldExistsByInterviewer_Id() {
        interviewService.existsByInterviewerId("1");

        verify(interviewServiceClient).existsByInterviewerId("1");
    }

    @Test
    void shouldFindAllByInterviewerId() {
        interviewService.findAllByInterviewerId("1");

        verify(interviewServiceClient).findAllByInterviewerId("1");
    }

    @Test
    void shouldBuildFallbackSaveAllInterviews() {
        interviewService.saveAllInterviews(new ArrayList<>());

        verify(interviewServiceClient).saveAllInterviews(new ArrayList<>());
    }
}