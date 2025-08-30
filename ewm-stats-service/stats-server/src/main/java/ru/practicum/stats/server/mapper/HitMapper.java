package ru.practicum.stats.server.mapper;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.server.model.Hit;

public class HitMapper {
    public static Hit toEntity(EndpointHitDto dto) {
        return Hit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dto.getTimestamp())
                .build();
    }
}