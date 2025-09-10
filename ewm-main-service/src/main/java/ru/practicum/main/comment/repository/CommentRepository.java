package ru.practicum.main.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByEventIdOrderByCreatedDesc(Long eventId, Pageable pageable);
    boolean existsByIdAndAuthorId(Long id, Long authorId);
}
