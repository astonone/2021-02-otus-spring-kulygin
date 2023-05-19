package ru.otus.kulygin.templateservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("criteria-service")
public interface CriteriaServiceClient {

    @GetMapping("interview-template-criteria/exists/{criteriaId}")
    boolean existsCriteriaById(@PathVariable String criteriaId);
}
