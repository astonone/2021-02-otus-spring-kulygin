package ru.otus.kulygin.interviewservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.kulygin.interviewservice.vo.InterviewTemplateVO;

@FeignClient("template-service")
public interface TemplateServiceClient {

    @GetMapping("/interview-template/{templateId}")
    InterviewTemplateVO getInterviewTemplateById(@PathVariable String templateId);

}
