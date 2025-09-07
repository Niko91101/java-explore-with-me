package ru.practicum.main.request.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.main.commons.enums.RequestStatus;
import ru.practicum.main.request.model.ParticipationRequest;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    @Query("""
            SELECT pr.event.id, COUNT(pr)
            FROM ParticipationRequest pr
            WHERE pr.status = :status
            AND pr.event.id IN :eventIds
            GROUP BY pr.event.id
            """)
    List<Object[]> countConfirmedByEventIds(Set<Long> eventIds, ru.practicum.main.commons.enums.RequestStatus status);


    long countByEventIdAndStatus(Long eventId, RequestStatus status);

    boolean existsByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<ParticipationRequest> findAllByRequesterId(Long requesterId);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    List<ParticipationRequest> findAllByEventIdAndStatus(Long eventId, RequestStatus status);

    List<ParticipationRequest> findAllByIdIn(Collection<Long> ids);

    Optional<ParticipationRequest> findByIdAndRequesterId(Long id, Long requesterId);
}
