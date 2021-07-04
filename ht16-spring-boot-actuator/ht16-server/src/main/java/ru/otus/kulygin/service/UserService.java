package ru.otus.kulygin.service;

import ru.otus.kulygin.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    UserDto save(UserDto userDto);

    Optional<UserDto> getById(String id);

    void deleteById(String id);

    boolean login(UserDto userDto);

    UserDto getUserByUsername(HttpServletRequest request);

}
