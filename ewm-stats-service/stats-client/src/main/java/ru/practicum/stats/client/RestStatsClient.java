package ru.practicum.stats.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

@Slf4j
public class RestStatsClient implements StatsClient {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String appName;

    public RestStatsClient(@NonNull RestTemplate restTemplate,
                           @NonNull String baseUrl,
                           @NonNull String appName) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.appName = appName;

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void saveHit(EndpointHitDto hit) {
        try {
            URI uri = URI.create(baseUrl + "/hit");
            RequestEntity<EndpointHitDto> req = RequestEntity
                    .post(uri)
                    .body(hit);
            restTemplate.exchange(req, Void.class);
            log.debug("Stats: saved hit app={} uri={} ip={}", hit.getApp(), hit.getUri(), hit.getIp());
        } catch (RestClientException ex) {
            log.warn("Stats: saveHit failed: {}", ex.getMessage());
        }
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        try {
            UriComponentsBuilder b = UriComponentsBuilder
                    .fromHttpUrl(baseUrl + "/stats")
                    .queryParam("start", encodeDate(start))
                    .queryParam("end", encodeDate(end))
                    .queryParam("unique", unique);

            if (uris != null) {
                for (String u : uris) {
                    b.queryParam("uris", u);
                }
            }

            URI uri = b.build(true).toUri();
            RequestEntity<Void> req = RequestEntity.get(uri).build();
            ResponseEntity<ViewStatsDto[]> resp = restTemplate.exchange(req, ViewStatsDto[].class);
            ViewStatsDto[] body = resp.getBody();
            return body == null ? List.of() : List.of(body);
        } catch (RestClientException ex) {
            log.warn("Stats: getStats failed: {}", ex.getMessage());
            return List.of();
        }
    }

    @Override
    public Map<String, Long> getStatsAsMap(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<ViewStatsDto> list = getStats(start, end, uris, unique);
        Map<String, Long> map = new HashMap<>();
        for (ViewStatsDto v : list) {
            map.put(v.getUri(), v.getHits() == null ? 0L : v.getHits());
        }
        return map;
    }

    private String encodeDate(@Nullable LocalDateTime dt) {
        LocalDateTime value = dt == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : dt;
        String formatted = value.format(FORMATTER);
        return URLEncoder.encode(formatted, StandardCharsets.UTF_8);
    }

    public EndpointHitDto newHit(String uri, String ip) {
        EndpointHitDto dto = EndpointHitDto.builder()
                .app(appName)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
        return dto;
    }
}
