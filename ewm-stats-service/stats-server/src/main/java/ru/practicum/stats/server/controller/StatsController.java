package ru.practicum.stats.server.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.server.dto.DateTimeParser;
import ru.practicum.stats.server.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<Void> saveHit(@Valid @RequestBody EndpointHitDto dto) {
        statsService.saveHit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(defaultValue = "false") boolean unique) {
        return statsService.getStats(
                DateTimeParser.parse(start),
                DateTimeParser.parse(end),
                uris,
                unique
        );
    }
}