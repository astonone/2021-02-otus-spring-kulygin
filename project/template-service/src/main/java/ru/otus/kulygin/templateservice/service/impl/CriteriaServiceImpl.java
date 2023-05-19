package ru.otus.kulygin.templateservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.templateservice.feign.CriteriaServiceClient;
import ru.otus.kulygin.templateservice.service.CriteriaService;

@Service
@AllArgsConstructor
public class CriteriaServiceImpl implements CriteriaService {

    private final CriteriaServiceClient criteriaServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackExistsCriteriaById")
    @Override
    public boolean existsCriteriaById(String criteriaId) {
        return criteriaServiceClient.existsCriteriaById(criteriaId);
    }

    public boolean buildFallbackExistsCriteriaById(String criteriaId) {
        return true;
    }
}
