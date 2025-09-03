package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDto> findAll(List<Long> ids, Pageable pageable) {
        if (ids != null && !ids.isEmpty()) {
            log.debug("Get users by ids: {}", ids);
            List<UserDto> all = userRepository.findAllByIdIn(ids)
                    .stream()
                    .map(UserMapper::toDto)
                    .toList();
            return new PageImpl<>(all);
        }
        log.debug("Get users paged: {}", pageable);
        return userRepository.findAll(pageable).map(UserMapper::toDto);
    }

    @Override
    @Transactional
    public UserDto create(NewUserRequest dto) {
        log.info("Create user: email={}, name={}", dto.getEmail(), dto.getName());
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new ConflictException("Email must be unique");
        }
        try {
            User saved = userRepository.save(UserMapper.toEntity(dto));
            return UserMapper.toDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Integrity constraint has been violated.", e);
        }
    }

    @Override
    @Transactional
    public void delete(long userId) {
        log.info("Delete user id={}", userId);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }
        try {
            userRepository.deleteById(userId);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Integrity constraint has been violated.", e);
        }
    }
}
