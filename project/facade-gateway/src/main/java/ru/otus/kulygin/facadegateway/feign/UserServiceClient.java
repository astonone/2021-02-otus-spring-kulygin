package ru.otus.kulygin.facadegateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.kulygin.facadegateway.vo.InterviewerVO;

@FeignClient("user-service")
public interface UserServiceClient {

    @GetMapping("interviewer/username/{username}")
    InterviewerVO getByUsername(@PathVariable String username);

}
