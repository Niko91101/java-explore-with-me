package ru.practicum.stats.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class RestStatsClient implements StatsClient {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String baseUrl;
    private final RestTemplate rest;
    private final ObjectMapper om;

    @Override
    public void saveHit(EndpointHitDto hit) {
        String url = baseUrl + "/hit";
        rest.postForEntity(url, hit, Void.class);
    }

    @Override
    public Map<String, Long> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        UriComponentsBuilder b = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/stats")
                .queryParam("start", FMT.format(start))
                .queryParam("end", FMT.format(end))
                .queryParam("unique", unique);

        if (!CollectionUtils.isEmpty(uris)) {
            for (String uri : uris) {
                b.queryParam("uris", uri);
            }
        }

        String url = b.toUriString();

        ResponseEntity<List<ViewStatsDto>> resp = rest.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ViewStatsDto>>() {
                });

        List<ViewStatsDto> list = Optional.ofNullable(resp.getBody()).orElse(Collections.emptyList());

        Map<String, Long> result = new HashMap<>();
        for (ViewStatsDto v : list) {
            result.put(v.getUri(), v.getHits());
        }
        return result;
    }
}
