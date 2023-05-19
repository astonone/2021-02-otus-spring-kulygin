package ru.otus.kulygin.interviewservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.kulygin.interviewservice.vo.CandidateVO;

@FeignClient("candidate-service")
public interface CandidateServiceClient {

    @GetMapping("/candidate/{candidateId}")
    CandidateVO getCandidateById(@PathVariable String candidateId);
}
