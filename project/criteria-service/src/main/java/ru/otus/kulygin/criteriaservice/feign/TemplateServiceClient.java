package ru.otus.kulygin.criteriaservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.kulygin.criteriaservice.vo.InterviewTemplateVO;

import java.util.List;

@FeignClient("template-service")
public interface TemplateServiceClient {

    @GetMapping("interview-template/criteria/exists/{id}")
    boolean existsTemplateByCriteriaId(@PathVariable String id);

    @GetMapping("interview-template/criteria/{criteriaId}")
    List<InterviewTemplateVO> findAllByCriteriaId(@PathVariable String criteriaId);

    @PostMapping("interview-template/list")
    List<InterviewTemplateVO> saveAll(@RequestBody List<InterviewTemplateVO> interviews);
}
