package ru.practicum.main.stats;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.config.StatsClientProperties;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsClient statsClient;
    private final StatsClientProperties props;

    @Override
    public void saveHit(HttpServletRequest request) {
        try {
            EndpointHitDto hit = EndpointHitDto.builder()
                    .app(props.getAppName())
                    .uri(request.getRequestURI())
                    .ip(getClientIp(request))
                    .timestamp(LocalDateTime.now())
                    .build();
            statsClient.saveHit(hit);
            log.debug("Stats hit sent: {}", hit);
        } catch (Exception e) {
            log.warn("Failed to send stats hit: {}", e.getMessage());
        }
    }

    @Override
    public Map<String, Long> getViews(LocalDateTime start,
                                      LocalDateTime end,
                                      List<String> uris,
                                      boolean unique) {
        try {
            return statsClient.getStats(start, end, uris, unique);
        } catch (Exception e) {
            log.warn("Failed to fetch stats: {}", e.getMessage());
            return Map.of();
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String h = request.getHeader("X-Forwarded-For");
        if (h != null && !h.isBlank()) {
            int comma = h.indexOf(',');
            return comma > 0 ? h.substring(0, comma).trim() : h.trim();
        }
        return request.getRemoteAddr();
    }
}
