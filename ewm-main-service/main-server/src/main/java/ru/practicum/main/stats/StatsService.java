package ru.practicum.main.stats;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsService {
    void saveHit(HttpServletRequest request);

    Map<String, Long> getViews(LocalDateTime start,
                               LocalDateTime end,
                               List<String> uris,
                               boolean unique);
}
