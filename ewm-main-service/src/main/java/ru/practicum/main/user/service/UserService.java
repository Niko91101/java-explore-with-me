package ru.practicum.main.user.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

public interface UserService {

    List<UserDto> findAll(List<Long> ids, Pageable pageable);

    UserDto create(NewUserRequest dto);

    void delete(Long userId);
}
