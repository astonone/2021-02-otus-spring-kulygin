package ru.otus.kulygin.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.repository.InterviewerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final InterviewerRepository interviewerRepository;

    public UserDetailsServiceImpl(InterviewerRepository interviewerRepository) {
        this.interviewerRepository = interviewerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Interviewer user = interviewerRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User with username: " + username + " was not found!");
        }
    }

}
