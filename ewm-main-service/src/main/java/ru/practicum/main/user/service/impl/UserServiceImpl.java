package ru.practicum.main.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;
import ru.practicum.main.user.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll(List<Long> ids, Pageable pageable) {
        List<User> entities;
        if (ids != null && !ids.isEmpty()) {
            entities = userRepository.findAllByIdIn(ids);
            log.debug("Fetched users by ids count={}", entities.size());
        } else {
            entities = userRepository.findAllBy(pageable);
            log.debug("Fetched users page size={}", entities.size());
        }
        return entities.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto create(NewUserRequest dto) {
        User toSave = UserMapper.toEntity(dto);
        User saved = userRepository.save(toSave);
        log.info("Created user id={}", saved.getId());
        return UserMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        try {
            userRepository.deleteById(userId);
            log.info("Deleted user id={}", userId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }
    }
}
