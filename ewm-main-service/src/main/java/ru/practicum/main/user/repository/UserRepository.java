package ru.practicum.main.user.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIdIn(List<Long> ids);

    boolean existsByEmailIgnoreCase(String email);

    List<User> findAllBy(Pageable pageable);
}
