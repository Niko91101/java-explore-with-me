package ru.practicum.main.comment.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.comment.service.CommentService;
import ru.practicum.main.commons.enums.EventState;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.ForbiddenException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;
import ru.practicum.main.util.OffsetBasedPageRequest;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto dto) {
        if (dto.getText() == null || dto.getText().isBlank()) {
            throw new BadRequestException("Comment text must not be blank");
        }
        if (dto.getText().length() > 1000) {
            throw new BadRequestException("Comment text exceeds 1000 chars");
        }

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found: " + eventId));

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Cannot comment a non-published event");
        }

        Comment saved = commentRepository.save(
                CommentMapper.toEntity(dto, event, author, LocalDateTime.now())
        );
        return CommentMapper.toDto(saved);
    }

    @Override
    public List<CommentDto> listComments(Long eventId, int from, int size) {
        if (from < 0 || size <= 0) {
            throw new BadRequestException("Invalid pagination parameters");
        }
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        return commentRepository.findByEventIdOrderByCreatedDesc(eventId, pageable)
                .stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteOwn(Long userId, Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment not found: " + commentId);
        }
        if (!commentRepository.existsByIdAndAuthorId(commentId, userId)) {
            throw new ForbiddenException("Only author can delete own comment");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void adminDelete(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment not found: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}
