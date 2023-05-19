package ru.otus.kulygin.candidateservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.candidateservice.feign.InterviewServiceClient;
import ru.otus.kulygin.candidateservice.service.InterviewService;
import ru.otus.kulygin.candidateservice.vo.InterviewVO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewServiceClient interviewServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackExistsByCandidate_Id")
    @Override
    public boolean existsByCandidateId(String candidateId) {
        return interviewServiceClient.existsByCandidateId(candidateId);
    }

    public boolean buildFallbackExistsByCandidate_Id(String candidateId) {
        return true;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackFindAllByCandidateId")
    @Override
    public List<InterviewVO> findAllByCandidateId(String candidateId) {
        return interviewServiceClient.findAllByCandidateId(candidateId);
    }

    public List<InterviewVO> buildFallbackFindAllByCandidateId(String candidateId) {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "buildFallbackSaveAllInterviews")
    @Override
    public List<InterviewVO> saveAllInterviews(List<InterviewVO> interviews) {
        return interviewServiceClient.saveAllInterviews(interviews);
    }

    public List<InterviewVO> buildFallbackSaveAllInterviews(List<InterviewVO> interviews) {
        return interviews;
    }
}
