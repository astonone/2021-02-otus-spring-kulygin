package ru.otus.kulygin.userservice.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.userservice.domain.Interviewer;
import ru.otus.kulygin.userservice.dto.InterviewerDto;
import ru.otus.kulygin.userservice.dto.pageable.InterviewerPageableDto;
import ru.otus.kulygin.userservice.enumerations.UserRoles;
import ru.otus.kulygin.userservice.exception.InterviewerDoesNotExistException;
import ru.otus.kulygin.userservice.exception.SecretKeyException;
import ru.otus.kulygin.userservice.exception.UsernameAlreadyExistException;
import ru.otus.kulygin.userservice.repository.InterviewerRepository;
import ru.otus.kulygin.userservice.service.InterviewerService;

import java.util.Optional;

@Service
public class InterviewerServiceImpl implements InterviewerService {

    private final InterviewerRepository interviewerRepository;
    private final MappingService mappingService;
    private final PasswordEncoder passwordEncoder;
    private final String secretKey;

    public InterviewerServiceImpl(InterviewerRepository interviewerRepository, MappingService mappingService,
                                  PasswordEncoder passwordEncoder, @Value("${app.admin.secret-key}") String secretKey) {
        this.interviewerRepository = interviewerRepository;
        this.mappingService = mappingService;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    @Override
    public InterviewerPageableDto findAll(Pageable pageable) {
        val interviewers = interviewerRepository.findAll(pageable);

        return InterviewerPageableDto.builder()
                .page(interviewers.getPageable().getPageNumber())
                .pageSize(interviewers.getPageable().getPageSize())
                .currentPageSize(interviewers.getContent().size())
                .totalSize(interviewers.getTotalElements())
                .totalPageSize(interviewers.getTotalPages())
                .interviewers(mappingService.mapAsList(interviewers.getContent(), InterviewerDto.class))
                .build();
    }

    @Override
    public InterviewerPageableDto findAll() {
        val interviewers = interviewerRepository.findAll();

        return InterviewerPageableDto.builder()
                .interviewers(mappingService.mapAsList(interviewers, InterviewerDto.class))
                .build();
    }

    @Override
    public InterviewerDto getById(String id) {
        return interviewerRepository.findById(id)
                .map(interviewer -> mappingService.map(interviewer, InterviewerDto.class))
                .orElseThrow(InterviewerDoesNotExistException::new);
    }

    @Override
    public InterviewerDto save(InterviewerDto interviewerDto) {
        Interviewer forSave = Interviewer.builder().build();
        if (interviewerDto.isCreate()) {
            if (interviewerRepository.existsByUsername(interviewerDto.getUsername())) {
                throw new UsernameAlreadyExistException();
            }
            forSave.setFirstName(interviewerDto.getFirstName());
            forSave.setLastName(interviewerDto.getLastName());
            forSave.setPositionType(interviewerDto.getPositionType());
            forSave.setUsername(interviewerDto.getUsername());
            forSave.setPassword(passwordEncoder.encode(interviewerDto.getPassword()));
            forSave.setAccountNonExpired(true);
            forSave.setAccountNonLocked(true);
            forSave.setCredentialsNonExpired(true);
            forSave.setEnabled(true);
            if (interviewerDto.getSecretKey() != null && !interviewerDto.getSecretKey().isEmpty()) {
                if (passwordEncoder.matches(interviewerDto.getSecretKey(), this.secretKey)) {
                    forSave.setRole(UserRoles.DEVELOPER.getRoleName());
                } else {
                    throw new SecretKeyException();
                }
            } else {
                forSave.setRole(UserRoles.HR.getRoleName());
            }
        } else {
            Optional<Interviewer> interviewerById = Optional.empty();
            if (interviewerDto.getId() != null) {
                interviewerById = interviewerRepository.findById(interviewerDto.getId());
                if (interviewerById.isEmpty()) {
                    throw new InterviewerDoesNotExistException();
                }
            }
            forSave.setId(interviewerById.get().getId());
            forSave.setFirstName(interviewerDto.getFirstName());
            forSave.setLastName(interviewerDto.getLastName());
            forSave.setPositionType(interviewerDto.getPositionType());
            forSave.setUsername(interviewerById.get().getUsername());
            forSave.setPassword(interviewerById.get().getPassword());
            forSave.setAccountNonExpired(interviewerById.get().isAccountNonExpired());
            forSave.setAccountNonLocked(interviewerById.get().isAccountNonLocked());
            forSave.setCredentialsNonExpired(interviewerById.get().isCredentialsNonExpired());
            forSave.setEnabled(interviewerById.get().isEnabled());
            forSave.setRole(interviewerById.get().getRole());
        }
        return mappingService.map(interviewerRepository.save(forSave), InterviewerDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (interviewerRepository.existsById(id)) {
            interviewerRepository.deleteById(id);
        }
    }

    @Override
    public String login(InterviewerDto interviewerDto) {
        Interviewer user = interviewerRepository.findByUsername(interviewerDto.getUsername());
        if (user != null) {
            return passwordEncoder.matches(interviewerDto.getPassword(), user.getPassword()) ? user.getId() : null;
        }
        return null;
    }

    @Override
    public Interviewer findByUsername(String username) {
        return interviewerRepository.findByUsername(username);
    }
}
