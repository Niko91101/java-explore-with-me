package ru.practicum.main.stats.impl;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.config.AppProperties;
import ru.practicum.main.stats.StatsService;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsClient statsClient;
    private final AppProperties appProperties;

    @Override
    public void recordHit(HttpServletRequest request) {
        EndpointHitDto hit = EndpointHitDto.builder()
                .app(appProperties.getName())
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();

        statsClient.saveHit(hit);
        log.debug("Stats recorded uri={}", hit.getUri());
    }

    @Override
    public Map<String, Long> getViews(List<String> uris) {
        if (uris == null || uris.isEmpty()) {
            return new HashMap<>();
        }
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.now();

        List<ViewStatsDto> stats = statsClient.getStats(start, end, uris, true);

        Map<String, Long> map = new HashMap<>();
        for (ViewStatsDto s : stats) {
            map.put(s.getUri(), s.getHits() == null ? 0L : s.getHits());
        }
        return map;
    }
}
