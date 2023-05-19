package ru.otus.kulygin.interviewservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.interviewservice.feign.TemplateServiceClient;
import ru.otus.kulygin.interviewservice.service.TemplateService;
import ru.otus.kulygin.interviewservice.vo.InterviewTemplateVO;

import java.util.ArrayList;

import static ru.otus.kulygin.interviewservice.service.impl.CandidateServiceImpl.DEFAULT_EMPTY_VALUE;

@Service
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateServiceClient templateServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackGetInterviewTemplateById")
    @Override
    public InterviewTemplateVO getInterviewTemplateById(String templateId) {
        return templateServiceClient.getInterviewTemplateById(templateId);
    }

    public InterviewTemplateVO buildFallbackGetInterviewTemplateById(String templateId) {
        return InterviewTemplateVO.builder()
                .id(templateId)
                .positionName(DEFAULT_EMPTY_VALUE)
                .criterias(new ArrayList<>())
                .build();
    }
}
