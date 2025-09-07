package ru.practicum.stats.client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

public interface StatsClient {

    void saveHit(EndpointHitDto hit);

    /**
     * Получить статистику просмотров по списку uri.
     * @param start  начало периода (включительно)
     * @param end    конец периода (включительно)
     * @param uris   список URI
     * @param unique учитывать только уникальные ip
     * @return список агрегированных просмотров
     */
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    Map<String, Long> getStatsAsMap(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
