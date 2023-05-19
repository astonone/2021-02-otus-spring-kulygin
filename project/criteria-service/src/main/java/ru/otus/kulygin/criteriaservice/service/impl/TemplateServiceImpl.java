package ru.otus.kulygin.criteriaservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.criteriaservice.feign.TemplateServiceClient;
import ru.otus.kulygin.criteriaservice.service.TemplateService;
import ru.otus.kulygin.criteriaservice.vo.InterviewTemplateVO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateServiceClient templateServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackExistsTemplateByCriteriaId")
    @Override
    public boolean existsTemplateByCriteriaId(String id) {
        return templateServiceClient.existsTemplateByCriteriaId(id);
    }

    public boolean buildFallbackExistsTemplateByCriteriaId(String id) {
        return true;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackFindAllByCriteriaId")
    @Override
    public List<InterviewTemplateVO> findAllByCriteriaId(String criteriaId) {
        return templateServiceClient.findAllByCriteriaId(criteriaId);
    }

    public List<InterviewTemplateVO> buildFallbackFindAllByCriteriaId(String criteriaId) {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "buildFallbackSaveAll")
    @Override
    public List<InterviewTemplateVO> saveAll(List<InterviewTemplateVO> interviews) {
        return templateServiceClient.saveAll(interviews);
    }

    public List<InterviewTemplateVO> buildFallbackSaveAll(List<InterviewTemplateVO> interviews) {
        return interviews;
    }

}
