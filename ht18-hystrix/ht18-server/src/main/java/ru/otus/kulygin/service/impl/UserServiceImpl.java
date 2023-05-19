package ru.otus.kulygin.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.User;
import ru.otus.kulygin.dto.UserDto;
import ru.otus.kulygin.enumerations.UserAuthorities;
import ru.otus.kulygin.exception.SecretKeyException;
import ru.otus.kulygin.exception.UserDoesNotExistException;
import ru.otus.kulygin.exception.UsernameAlreadyExistException;
import ru.otus.kulygin.repository.UserRepository;
import ru.otus.kulygin.service.UserService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Optional;

import static ru.otus.kulygin.service.impl.AuthorServiceImpl.N_A;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MappingService mappingService;
    private final PasswordEncoder passwordEncoder;
    private final String secretKey;

    public UserServiceImpl(UserRepository userRepository, MappingService mappingService,
                           PasswordEncoder passwordEncoder, @Value("${app.admin.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.mappingService = mappingService;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    @HystrixCommand(fallbackMethod="buildFallbackCreateUser")
    @Override
    public UserDto save(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UsernameAlreadyExistException();
        }
        User forSave = User.builder().build();
        Optional<User> userById = Optional.empty();
        if (userDto.getId() != null) {
            userById = userRepository.findById(userDto.getId());
            if (userById.isEmpty()) {
                throw new UserDoesNotExistException();
            }
        }
        forSave.setId(userById.map(User::getId).orElse(null));
        forSave.setUsername(userDto.getUsername());
        forSave.setPassword(passwordEncoder.encode(userDto.getPassword()));
        forSave.setAccountNonExpired(true);
        forSave.setAccountNonLocked(true);
        forSave.setCredentialsNonExpired(true);
        forSave.setEnabled(true);
        if (userDto.getSecretKey() != null && !userDto.getSecretKey().isEmpty()) {
            if (passwordEncoder.matches(userDto.getSecretKey(), this.secretKey)) {
                forSave.setAuthority(UserAuthorities.ADMIN.getAuthority());
            } else {
                throw new SecretKeyException();
            }
        } else {
            forSave.setAuthority(UserAuthorities.USER.getAuthority());
        }
        return mappingService.map(userRepository.save(forSave), UserDto.class);
    }

    public UserDto buildFallbackCreateUser(UserDto userDto) {
        return UserDto.builder()
                .id(N_A)
                .build();
    }

    @HystrixCommand(fallbackMethod="buildFallbackUser")
    @Override
    public Optional<UserDto> getById(String id) {
        return userRepository.findById(id).map(user -> mappingService.map(user, UserDto.class));
    }

    public Optional<UserDto> buildFallbackUser(String id) {
        return Optional.of(UserDto.builder()
                .id(N_A)
                .build());
    }

    @Secured("ROLE_ADMIN")
    @HystrixCommand(fallbackMethod="buildFallbackDeleteUser")
    @Override
    public void deleteById(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserDoesNotExistException();
        }
        userRepository.deleteById(id);
    }

    public void buildFallbackDeleteUser(String id) {
        // nothing
    }

    @HystrixCommand(fallbackMethod="buildFallbackLogin")
    @Override
    public boolean login(UserDto userDto) {
        User userByEmail = userRepository.findByUsername(userDto.getUsername());
        if (userByEmail != null) {
            return passwordEncoder.matches(userDto.getPassword(), userByEmail.getPassword());
        }
        return false;
    }

    public boolean buildFallbackLogin(UserDto userDto) {
        return false;
    }

    @HystrixCommand(fallbackMethod="buildFallbackUserByUsername")
    @Override
    public UserDto getUserByUsername(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        String username = new String(Base64.getDecoder().decode(authToken)).split(":")[0];
        User userByUserName = userRepository.findByUsername(username);
        return mappingService.map(userByUserName, UserDto.class);
    }

    public UserDto buildFallbackUserByUsername(HttpServletRequest request) {
        return UserDto.builder()
                .id(N_A)
                .build();
    }

}
