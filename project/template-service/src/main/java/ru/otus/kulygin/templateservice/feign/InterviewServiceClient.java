package ru.otus.kulygin.templateservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.kulygin.templateservice.vo.InterviewVO;

import java.util.List;

@FeignClient("interview-service")
public interface InterviewServiceClient {

    @GetMapping("interview/template/exist/{templateId}")
    boolean existByTemplateId(@PathVariable String templateId);

    @GetMapping("interview/template/{templateId}/planned")
    List<InterviewVO> findAllByTemplateId(@PathVariable String templateId);

    @PostMapping("interview/list")
    List<InterviewVO> saveAll(@RequestBody List<InterviewVO> interviews);
}
