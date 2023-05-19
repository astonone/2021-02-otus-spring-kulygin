package ru.otus.kulygin.templateservice.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.templateservice.feign.InterviewServiceClient;
import ru.otus.kulygin.templateservice.service.InterviewService;
import ru.otus.kulygin.templateservice.service.impl.InterviewServiceImpl;
import ru.otus.kulygin.templateservice.vo.InterviewVO;

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
    void shouldExistByTemplateId() {
        interviewService.existByTemplateId("1");

        verify(interviewServiceClient).existByTemplateId("1");
    }

    @Test
    void shouldFindAllByTemplateId() {
        interviewService.findAllByTemplateId("1");

        verify(interviewServiceClient).findAllByTemplateId("1");
    }

    @Test
    void shouldSaveAll() {
        val interviews = new ArrayList<InterviewVO>();

        interviewService.saveAll(interviews);

        verify(interviewServiceClient).saveAll(interviews);
    }
}