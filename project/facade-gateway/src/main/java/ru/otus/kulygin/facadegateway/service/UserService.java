package ru.otus.kulygin.facadegateway.service;

import ru.otus.kulygin.facadegateway.vo.InterviewerVO;

public interface UserService {

    InterviewerVO getByUsername(String username);

}
