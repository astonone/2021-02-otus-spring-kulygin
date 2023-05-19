package ru.otus.kulygin.criteriaservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.criteriaservice.feign.InterviewServiceClient;
import ru.otus.kulygin.criteriaservice.service.InterviewService;
import ru.otus.kulygin.criteriaservice.vo.InterviewVO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewServiceClient interviewServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackFindAllPlannedByCriteriaId")
    @Override
    public List<InterviewVO> findAllPlannedByCriteriaId(String criteriaId) {
        return interviewServiceClient.findAllPlannedByCriteriaId(criteriaId);
    }

    public List<InterviewVO> buildFallbackFindAllPlannedByCriteriaId(String criteriaId) {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "buildFallbackSaveAll")
    @Override
    public List<InterviewVO> saveAll(List<InterviewVO> interviews) {
        return interviewServiceClient.saveAll(interviews);
    }

    public List<InterviewVO> buildFallbackSaveAll(List<InterviewVO> interviews) {
        return interviews;
    }
}
