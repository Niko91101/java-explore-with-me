package ru.practicum.main.comment.service;

import java.util.List;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;

public interface CommentService {
    CommentDto addComment(Long userId, Long eventId, NewCommentDto dto);
    List<CommentDto> listComments(Long eventId, int from, int size);
    void deleteOwn(Long userId, Long commentId);
    void adminDelete(Long commentId);
}
