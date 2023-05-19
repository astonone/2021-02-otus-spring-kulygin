package ru.otus.kulygin.facadegateway.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.facadegateway.feign.UserServiceClient;
import ru.otus.kulygin.facadegateway.service.UserService;
import ru.otus.kulygin.facadegateway.vo.InterviewerVO;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserServiceClient userServiceClient;

    @HystrixCommand(fallbackMethod = "buildFallbackGetByUsername")
    @Override
    public InterviewerVO getByUsername(String username) {
        return userServiceClient.getByUsername(username);
    }

    public InterviewerVO buildFallbackGetByUsername(String username) {
        return null;
    }
}
