package ru.otus.kulygin.userservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.userservice.feign.InterviewServiceClient;
import ru.otus.kulygin.userservice.service.InterviewService;
import ru.otus.kulygin.userservice.vo.InterviewVO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewServiceClient interviewServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackExistsByInterviewer_Id")
    @Override
    public boolean existsByInterviewerId(String interviewerId) {
        return interviewServiceClient.existsByInterviewerId(interviewerId);
    }

    public boolean buildFallbackExistsByInterviewer_Id(String interviewerId) {
        return true;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackFindAllByInterviewerId")
    @Override
    public List<InterviewVO> findAllByInterviewerId(String candidateId) {
        return interviewServiceClient.findAllByInterviewerId(candidateId);
    }

    public List<InterviewVO> buildFallbackFindAllByInterviewerId(String candidateId) {
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
