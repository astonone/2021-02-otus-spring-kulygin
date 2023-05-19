package ru.otus.kulygin.criteriaservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.kulygin.criteriaservice.vo.InterviewVO;

import java.util.List;

@FeignClient("interview-service")
public interface InterviewServiceClient {

    @GetMapping("interview/criteria/{criteriaId}/planned")
    List<InterviewVO> findAllPlannedByCriteriaId(@PathVariable String criteriaId);

    @PostMapping("interview/list")
    List<InterviewVO> saveAll(@RequestBody List<InterviewVO> interviews);

}
