package ru.otus.kulygin.templateservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.templateservice.feign.InterviewServiceClient;
import ru.otus.kulygin.templateservice.service.InterviewService;
import ru.otus.kulygin.templateservice.vo.InterviewVO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewServiceClient interviewServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackExistByTemplateId")
    @Override
    public boolean existByTemplateId(String templateId) {
        return interviewServiceClient.existByTemplateId(templateId);
    }

    public boolean buildFallbackExistByTemplateId(String templateId) {
        return true;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackFindAllByTemplateId")
    @Override
    public List<InterviewVO> findAllByTemplateId(String templateId) {
        return interviewServiceClient.findAllByTemplateId(templateId);
    }

    public List<InterviewVO> buildFallbackFindAllByTemplateId(String templateId) {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "buildFallbackSaveAll")
    @Override
    public List<InterviewVO> saveAll(List<InterviewVO> interviews) {
        return interviewServiceClient.saveAll(interviews);
    }

    public List<InterviewVO> buildFallbackSaveAll(List<InterviewVO> interviews) {
        return interviews;
    }
}
