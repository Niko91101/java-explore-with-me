package ru.practicum.main.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stats.client")
@Getter
@Setter
public class StatsClientProperties {
    private String baseUrl;
    private String appName;
    private int connectTimeoutMs = 1000;
    private int readTimeoutMs = 2000;
}
