package ru.practicum.main.comment.mapper;

import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

@UtilityClass
public class CommentMapper {

    public CommentDto toDto(Comment c) {
        return CommentDto.builder()
                .id(c.getId())
                .eventId(c.getEvent().getId())
                .authorId(c.getAuthor().getId())
                .text(c.getText())
                .created(c.getCreated())
                .build();
    }

    public Comment toEntity(NewCommentDto dto, Event event, User author, LocalDateTime created) {
        return Comment.builder()
                .event(event)
                .author(author)
                .text(dto.getText())
                .created(created)
                .build();
    }
}
