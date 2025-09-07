package ru.practicum.main.event.controller;

import jakarta.validation.Valid;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventUserRequest;
import ru.practicum.main.event.service.EventService;


@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getOwn(@PathVariable Long userId,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                      @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<EventShortDto> list = eventService.findOwnEvents(userId, from, size);
        log.debug("GET /users/{}/events -> {}", userId, list.size());
        return list;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto add(@PathVariable Long userId,
                            @Valid @RequestBody NewEventDto dto) {
        EventFullDto created = eventService.create(userId, dto);
        log.debug("POST /users/{}/events -> {}", userId, created.getId());
        return created;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getOwnEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        EventFullDto dto = eventService.getOwnEvent(userId, eventId);
        log.debug("GET /users/{}/events/{} -> {}", userId, eventId, dto.getId());
        return dto;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateOwn(@PathVariable Long userId,
                                  @PathVariable Long eventId,
                                  @Valid @RequestBody UpdateEventUserRequest req) {
        EventFullDto dto = eventService.updateOwnEvent(userId, eventId, req);
        log.debug("PATCH /users/{}/events/{} -> {}", userId, eventId, dto.getId());
        return dto;
    }
}