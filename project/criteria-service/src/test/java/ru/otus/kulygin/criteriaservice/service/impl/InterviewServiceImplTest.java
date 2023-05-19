package ru.otus.kulygin.criteriaservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.criteriaservice.feign.InterviewServiceClient;
import ru.otus.kulygin.criteriaservice.service.InterviewService;
import ru.otus.kulygin.criteriaservice.vo.InterviewVO;

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
    void shouldFindAllPlannedByCriteriaId() {
        interviewService.findAllPlannedByCriteriaId("1");

        verify(interviewServiceClient).findAllPlannedByCriteriaId("1");
    }

    @Test
    void shouldSaveAll() {
        final ArrayList<InterviewVO> interviews = new ArrayList<>();
        interviewService.saveAll(interviews);

        verify(interviewServiceClient).saveAll(interviews);
    }
}