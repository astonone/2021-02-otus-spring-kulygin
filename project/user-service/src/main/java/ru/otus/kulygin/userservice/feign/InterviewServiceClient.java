package ru.otus.kulygin.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.kulygin.userservice.vo.InterviewVO;

import java.util.List;

@FeignClient("interview-service")
public interface InterviewServiceClient {

    @GetMapping("interview/interviewer/exist/{interviewerId}")
    boolean existsByInterviewerId(@PathVariable String interviewerId);

    @GetMapping("interview/interviewer/{interviewerId}")
    List<InterviewVO> findAllByInterviewerId(@PathVariable("interviewerId") String candidateId);

    @PostMapping("interview/list")
    List<InterviewVO> saveAllInterviews(List<InterviewVO> interviews);

}
