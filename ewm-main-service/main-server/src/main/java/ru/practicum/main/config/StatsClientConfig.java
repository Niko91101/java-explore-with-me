package ru.practicum.main.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.client.RestStatsClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(StatsClientProperties.class)
@RequiredArgsConstructor
public class StatsClientConfig {

    private final StatsClientProperties props;
    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate statsRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(props.getConnectTimeoutMs()))
                .setReadTimeout(Duration.ofMillis(props.getReadTimeoutMs()))
                .build();
    }

    @Bean
    public StatsClient statsClient(RestTemplate statsRestTemplate) {
        return new RestStatsClient(props.getBaseUrl(), statsRestTemplate, objectMapper);
    }
}
