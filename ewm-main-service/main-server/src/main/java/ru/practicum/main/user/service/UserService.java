package ru.practicum.main.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import java.util.List;

public interface UserService {
    Page<UserDto> findAll(List<Long> ids, Pageable pageable);

    UserDto create(NewUserRequest dto);

    void delete(long userId);
}
