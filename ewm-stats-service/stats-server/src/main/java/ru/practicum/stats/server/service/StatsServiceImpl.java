package ru.practicum.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.server.exception.BadRequestException;
import ru.practicum.stats.server.mapper.HitMapper;
import ru.practicum.stats.server.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final HitRepository repository;

    @Override
    @Transactional
    public void saveHit(EndpointHitDto dto) {
        repository.save(HitMapper.toEntity(dto));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (end.isBefore(start)) {
            throw new BadRequestException("end must not be before start");
        }
        boolean hasUris = uris != null && !uris.isEmpty();
        if (unique) {
            return hasUris ? repository.findStatsUniqueByUris(start, end, uris)
                    : repository.findStatsUnique(start, end);
        } else {
            return hasUris ? repository.findStatsByUris(start, end, uris)
                    : repository.findStats(start, end);
        }
    }
}