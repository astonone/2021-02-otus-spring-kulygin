package ru.otus.kulygin.interviewservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.interviewservice.feign.CandidateServiceClient;
import ru.otus.kulygin.interviewservice.service.CandidateService;
import ru.otus.kulygin.interviewservice.vo.CandidateVO;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    public static final String DEFAULT_EMPTY_VALUE = "N/A";

    private final CandidateServiceClient candidateServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackGetCandidateById")
    @Override
    public CandidateVO getCandidateById(String candidateId) {
        return candidateServiceClient.getCandidateById(candidateId);
    }

    public CandidateVO buildFallbackGetCandidateById(String candidateId) {
        return CandidateVO.builder()
                .id(candidateId)
                .firstName(DEFAULT_EMPTY_VALUE)
                .lastName(DEFAULT_EMPTY_VALUE)
                .claimingPosition(DEFAULT_EMPTY_VALUE)
                .build();
    }
}
