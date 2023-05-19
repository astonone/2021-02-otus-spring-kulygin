package ru.otus.kulygin.candidateservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.candidateservice.feign.InterviewServiceClient;
import ru.otus.kulygin.candidateservice.service.InterviewService;
import ru.otus.kulygin.candidateservice.vo.InterviewVO;

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
    void existsByCandidate_Id() {
        interviewService.existsByCandidateId("1");

        verify(interviewServiceClient).existsByCandidateId("1");
    }

    @Test
    void findAllByCandidateId() {
        interviewService.findAllByCandidateId("1");

        verify(interviewServiceClient).findAllByCandidateId("1");
    }

    @Test
    void buildFallbackSaveAllInterviews() {
        final ArrayList<InterviewVO> interviews = new ArrayList<>();
        interviewService.saveAllInterviews(interviews);

        verify(interviewServiceClient).saveAllInterviews(interviews);
    }
}