package ru.otus.kulygin.interviewservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.interviewservice.feign.InterviewerServiceClient;
import ru.otus.kulygin.interviewservice.service.InterviewerService;
import ru.otus.kulygin.interviewservice.vo.InterviewerVO;

import static ru.otus.kulygin.interviewservice.service.impl.CandidateServiceImpl.DEFAULT_EMPTY_VALUE;

@Service
@AllArgsConstructor
public class InterviewerServiceImpl implements InterviewerService {

    public static final String DEFAULT_ROLE = "ROLE_DEVELOPER";
    private final InterviewerServiceClient interviewerServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackGetInterviewerById")
    @Override
    public InterviewerVO getInterviewerById(String interviewerId) {
        return interviewerServiceClient.getInterviewerById(interviewerId);
    }

    public InterviewerVO buildFallbackGetInterviewerById(String interviewerId) {
        return InterviewerVO.builder()
                .id(interviewerId)
                .firstName(DEFAULT_EMPTY_VALUE)
                .lastName(DEFAULT_EMPTY_VALUE)
                .positionType(DEFAULT_EMPTY_VALUE)
                .role(DEFAULT_ROLE)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}
