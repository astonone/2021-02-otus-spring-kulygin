package ru.otus.kulygin.facadegateway.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.facadegateway.service.UserService;
import ru.otus.kulygin.facadegateway.vo.InterviewerVO;
import ru.otus.kulygin.facadegateway.vo.User;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final MappingService mappingService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InterviewerVO interviewer = userService.getByUsername(username);
        User user = mappingService.map(interviewer, User.class);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User with username: " + username + " was not found!");
        }
    }

}
