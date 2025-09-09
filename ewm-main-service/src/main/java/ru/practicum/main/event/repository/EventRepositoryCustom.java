package ru.practicum.main.event.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.commons.enums.EventState;
import ru.practicum.main.event.model.Event;

public interface EventRepositoryCustom {

    List<Event> searchPublic(String text,
                             List<Long> categories,
                             Boolean paid,
                             LocalDateTime rangeStart,
                             LocalDateTime rangeEnd,
                             EventState state,
                             Pageable pageable);

    List<Event> searchAdmin(List<Long> users,
                            List<EventState> states,
                            List<Long> categories,
                            LocalDateTime rangeStart,
                            LocalDateTime rangeEnd,
                            Pageable pageable);

    List<Event> findByIds(Set<Long> ids);
}
