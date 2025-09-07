package ru.practicum.main.event.service;

import java.util.List;

import ru.practicum.main.commons.enums.EventState;
import ru.practicum.main.commons.enums.PublicSort;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.main.event.dto.UpdateEventUserRequest;

public interface EventService {

    List<EventShortDto> searchPublic(String text,
                                     List<Long> categories,
                                     Boolean paid,
                                     String rangeStart,
                                     String rangeEnd,
                                     Boolean onlyAvailable,
                                     PublicSort sort,
                                     Integer from,
                                     Integer size);

    EventFullDto getPublishedById(Long id);

    List<EventFullDto> searchAdmin(List<Long> users,
                                   List<EventState> states,
                                   List<Long> categories,
                                   String rangeStart,
                                   String rangeEnd,
                                   Integer from,
                                   Integer size);

    EventFullDto adminUpdate(Long eventId, UpdateEventAdminRequest request);

    List<EventShortDto> findOwnEvents(Long userId, Integer from, Integer size);

    EventFullDto create(Long userId, NewEventDto dto);

    EventFullDto getOwnEvent(Long userId, Long eventId);

    EventFullDto updateOwnEvent(Long userId, Long eventId, UpdateEventUserRequest request);
}
