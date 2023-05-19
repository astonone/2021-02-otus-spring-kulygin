package ru.otus.kulygin.candidateservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.kulygin.candidateservice.vo.InterviewVO;

import java.util.List;

@FeignClient("interview-service")
public interface InterviewServiceClient {

    @GetMapping("interview/candidate/exist/{candidateId}")
    boolean existsByCandidateId(@PathVariable String candidateId);

    @GetMapping("interview/candidate/{candidateId}")
    List<InterviewVO> findAllByCandidateId(@PathVariable("candidateId") String candidateId);

    @PostMapping("interview/list")
    List<InterviewVO> saveAllInterviews(List<InterviewVO> interviews);

}
