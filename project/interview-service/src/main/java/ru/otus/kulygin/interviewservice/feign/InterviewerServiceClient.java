package ru.otus.kulygin.interviewservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.kulygin.interviewservice.vo.InterviewerVO;

@FeignClient("user-service")
public interface InterviewerServiceClient {

    @GetMapping("/interviewer/{interviewerId}")
    InterviewerVO getInterviewerById(@PathVariable String interviewerId);
}
