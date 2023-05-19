package ru.otus.kulygin.userservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.userservice.BaseTest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = InterviewerServiceImpl.class)
class InterviewerServiceImplTest extends BaseTest {

    @Autowired
    private InterviewerService interviewerService;

    @MockBean
    private InterviewerRepository interviewerRepository;

    @MockBean
    private MappingService mappingService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldFindAllPageable() {
        final Page<Interviewer> interviewerPage = new PageImpl<>(getUserList(), getPageRequestP0PS10(), 10);
        when(interviewerRepository.findAll(getPageRequestP0PS10())).thenReturn(interviewerPage);
        when(mappingService.mapAsList(interviewerPage.getContent(), InterviewerDto.class)).thenReturn(getUserListDto());

        final InterviewerPageableDto result = interviewerService.findAll(getPageRequestP0PS10());

        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(interviewerPage.getPageable().getPageNumber());
        assertThat(result.getPageSize()).isEqualTo(interviewerPage.getPageable().getPageSize());
        assertThat(result.getCurrentPageSize()).isEqualTo(interviewerPage.getContent().size());
        assertThat(result.getTotalSize()).isEqualTo(interviewerPage.getTotalElements());
        assertThat(result.getTotalPageSize()).isEqualTo(interviewerPage.getTotalPages());
        assertThat(result.getInterviewers()).isEqualTo(getUserListDto());
    }

    @Test
    void shouldFindAll() {
        when(interviewerRepository.findAll()).thenReturn(getUserList());
        when(mappingService.mapAsList(getUserList(), InterviewerDto.class)).thenReturn(getUserListDto());

        final InterviewerPageableDto result = interviewerService.findAll();

        assertThat(result).isNotNull();
        assertThat(result.getInterviewers()).isEqualTo(getUserListDto());
    }

    @Test
    void shouldGetById() {
        when(interviewerRepository.findById("1")).thenReturn(Optional.of(getUserList().get(0)));
        when(mappingService.map(getUserList().get(0), InterviewerDto.class)).thenReturn(getUserListDto().get(0));

        final InterviewerDto result = interviewerService.getById("1");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(getUserListDto().get(0));
    }

    @Test
    void shouldNotGetByIdBecauseUserDoesNotExists() {
        assertThatThrownBy(() -> interviewerService.getById("1"))
                .isInstanceOf(InterviewerDoesNotExistException.class);
    }

    @Test
    void shouldSaveInCreateModeWithDevRole() {
        final InterviewerDto interviewerDto = getUserListDto().get(0);
        interviewerDto.setCreate(true);
        when(interviewerRepository.existsByUsername(interviewerDto.getUsername())).thenReturn(false);
        final String password = "123";
        final String encodedPassword = "$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci";
        interviewerDto.setPassword(password);
        when(passwordEncoder.encode(interviewerDto.getPassword())).thenReturn(encodedPassword);
        interviewerDto.setSecretKey("mySecret777");
        when(passwordEncoder.matches(interviewerDto.getSecretKey(), "$2a$10$Uke6zNBEQbaDz5MVLlX8ze0.pG5bMO2aF68QpYrejxbX8uUeDJ.lq")).thenReturn(true);
        final Interviewer userForSave = getUserList().get(0);
        userForSave.setRole(UserRoles.DEVELOPER.getRoleName());
        userForSave.setPassword(encodedPassword);
        when(interviewerRepository.save(userForSave)).thenReturn(userForSave);
        final InterviewerDto savedUser = InterviewerDto.builder()
                .firstName(userForSave.getFirstName())
                .lastName(userForSave.getLastName())
                .username(userForSave.getUsername())
                .password(encodedPassword)
                .role(userForSave.getRole())
                .positionType(userForSave.getPositionType())
                .enabled(userForSave.isEnabled())
                .accountNonExpired(userForSave.isAccountNonExpired())
                .accountNonLocked(userForSave.isAccountNonLocked())
                .credentialsNonExpired(userForSave.isCredentialsNonExpired())
                .build();
        when(mappingService.map(userForSave, InterviewerDto.class)).thenReturn(savedUser);

        final InterviewerDto result = interviewerService.save(interviewerDto);

        assertThat(result).isEqualTo(savedUser);
    }

    @Test
    void shouldNotSaveInCreateModeWithDevRoleBecauseWrongSecretKey() {
        final InterviewerDto interviewerDto = getUserListDto().get(0);
        interviewerDto.setCreate(true);
        when(interviewerRepository.existsByUsername(interviewerDto.getUsername())).thenReturn(false);
        final String password = "123";
        final String encodedPassword = "$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci";
        interviewerDto.setPassword(password);
        when(passwordEncoder.encode(interviewerDto.getPassword())).thenReturn(encodedPassword);
        interviewerDto.setSecretKey("mySecret777");
        when(passwordEncoder.matches(interviewerDto.getSecretKey(), "$2a$10$Uke6zNBEQbaDz5MVLlX8ze0.pG5bMO2aF68QpYrejxbX8uUeDJ.lq")).thenReturn(false);

        assertThatThrownBy(() -> interviewerService.save(interviewerDto))
                .isInstanceOf(SecretKeyException.class);
    }

    @Test
    void shouldSaveInCreateModeWithHrRole() {
        final InterviewerDto interviewerDto = getUserListDto().get(0);
        interviewerDto.setCreate(true);
        when(interviewerRepository.existsByUsername(interviewerDto.getUsername())).thenReturn(false);
        final String password = "123";
        final String encodedPassword = "$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci";
        interviewerDto.setPassword(password);
        when(passwordEncoder.encode(interviewerDto.getPassword())).thenReturn(encodedPassword);
        final Interviewer userForSave = getUserList().get(0);
        userForSave.setRole(UserRoles.HR.getRoleName());
        userForSave.setPassword(encodedPassword);
        when(interviewerRepository.save(userForSave)).thenReturn(userForSave);
        final InterviewerDto savedUser = InterviewerDto.builder()
                .firstName(userForSave.getFirstName())
                .lastName(userForSave.getLastName())
                .username(userForSave.getUsername())
                .password(encodedPassword)
                .role(userForSave.getRole())
                .positionType(userForSave.getPositionType())
                .enabled(userForSave.isEnabled())
                .accountNonExpired(userForSave.isAccountNonExpired())
                .accountNonLocked(userForSave.isAccountNonLocked())
                .credentialsNonExpired(userForSave.isCredentialsNonExpired())
                .build();
        when(mappingService.map(userForSave, InterviewerDto.class)).thenReturn(savedUser);

        final InterviewerDto result = interviewerService.save(interviewerDto);

        assertThat(result).isEqualTo(savedUser);
    }

    @Test
    void shouldNotSaveInCreateModeWBecauseUsernameAlreadyUsed() {
        final InterviewerDto interviewerDto = getUserListDto().get(0);
        interviewerDto.setCreate(true);
        when(interviewerRepository.existsByUsername(interviewerDto.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> interviewerService.save(interviewerDto))
                .isInstanceOf(UsernameAlreadyExistException.class);
    }

    @Test
    void shouldNotSaveInUpdateModeWBecauseUsernameAlreadyUsed() {
        final InterviewerDto interviewerDto = getUserListDto().get(0);
        interviewerDto.setId("1");
        when(interviewerRepository.findById(interviewerDto.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> interviewerService.save(interviewerDto))
                .isInstanceOf(InterviewerDoesNotExistException.class);
    }

    @Test
    void shouldSaveInUpdateMode() {
        final InterviewerDto interviewerDto = getUserListDto().get(0);
        interviewerDto.setId("1");
        interviewerDto.setPositionType("Super position");
        when(interviewerRepository.findById(interviewerDto.getId())).thenReturn(Optional.of(getUserList().get(0)));
        final Interviewer userForSave = getUserList().get(0);
        userForSave.setPositionType(interviewerDto.getPositionType());
        when(interviewerRepository.save(userForSave)).thenReturn(userForSave);
        final InterviewerDto savedUser = InterviewerDto.builder()
                .firstName(userForSave.getFirstName())
                .lastName(userForSave.getLastName())
                .username(userForSave.getUsername())
                .password(userForSave.getPassword())
                .role(userForSave.getRole())
                .positionType(userForSave.getPositionType())
                .enabled(userForSave.isEnabled())
                .accountNonExpired(userForSave.isAccountNonExpired())
                .accountNonLocked(userForSave.isAccountNonLocked())
                .credentialsNonExpired(userForSave.isCredentialsNonExpired())
                .build();
        when(mappingService.map(userForSave, InterviewerDto.class)).thenReturn(savedUser);

        final InterviewerDto result = interviewerService.save(interviewerDto);

        assertThat(result).isEqualTo(savedUser);
    }

    @Test
    void shouldDeleteById() {
        when(interviewerRepository.existsById("1")).thenReturn(true);
        interviewerService.deleteById("1");

        verify(interviewerRepository).existsById("1");
        verify(interviewerRepository).deleteById("1");
    }

    @Test
    void shouldReturnIdViaLogin() {
        final InterviewerDto interviewerDto = InterviewerDto.builder()
                .username("user")
                .password("password")
                .build();
        final Interviewer returnedUser = Interviewer.builder()
                .id("1")
                .username("user")
                .password("password")
                .build();
        when(interviewerRepository.findByUsername(interviewerDto.getUsername())).thenReturn(returnedUser);
        when(passwordEncoder.matches(interviewerDto.getPassword(), returnedUser.getPassword())).thenReturn(true);

        final String result = interviewerService.login(interviewerDto);

        assertThat(result).isEqualTo(returnedUser.getId());
    }

    @Test
    void shouldReturnNullViaLoginBecauseUserDoesNotExist() {
        final InterviewerDto interviewerDto = InterviewerDto.builder()
                .username("user")
                .password("password")
                .build();
        when(interviewerRepository.findByUsername(interviewerDto.getUsername())).thenReturn(null);

        final String result = interviewerService.login(interviewerDto);

        assertThat(result).isNull();
    }

    @Test
    void shouldReturnNullViaLoginBecausePasswordDoesntMatch() {
        final InterviewerDto interviewerDto = InterviewerDto.builder()
                .username("user")
                .password("password")
                .build();
        final Interviewer returnedUser = Interviewer.builder()
                .id("1")
                .username("user")
                .password("password2")
                .build();
        when(interviewerRepository.findByUsername(interviewerDto.getUsername())).thenReturn(returnedUser);
        when(passwordEncoder.matches(interviewerDto.getPassword(), returnedUser.getPassword())).thenReturn(false);

        final String result = interviewerService.login(interviewerDto);

        assertThat(result).isNull();
    }

    @Test
    void shouldFindByUsername() {
        when(interviewerRepository.findByUsername(getUserList().get(0).getUsername())).thenReturn(getUserList().get(0));

        final Interviewer result = interviewerService.findByUsername(getUserList().get(0).getUsername());

        assertThat(result).isEqualTo(getUserList().get(0));
    }
}