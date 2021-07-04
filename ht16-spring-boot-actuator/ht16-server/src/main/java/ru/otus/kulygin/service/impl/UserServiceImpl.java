package ru.otus.kulygin.service.impl;

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

    @Override
    public Optional<UserDto> getById(String id) {
        return userRepository.findById(id).map(user -> mappingService.map(user, UserDto.class));
    }

    @Secured("ROLE_ADMIN")
    @Override
    public void deleteById(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserDoesNotExistException();
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean login(UserDto userDto) {
        User userByEmail = userRepository.findByUsername(userDto.getUsername());
        if (userByEmail != null) {
            return passwordEncoder.matches(userDto.getPassword(), userByEmail.getPassword());
        }
        return false;
    }

    @Override
    public UserDto getUserByUsername(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        String username = new String(Base64.getDecoder().decode(authToken)).split(":")[0];
        User userByUserName = userRepository.findByUsername(username);
        return mappingService.map(userByUserName, UserDto.class);
    }

}
