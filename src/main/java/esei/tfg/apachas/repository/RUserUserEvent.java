package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.UserUserEvent;
import esei.tfg.apachas.entity.id.UserUserEventId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RUserUserEvent")
public interface RUserUserEvent extends CrudRepository<UserUserEvent, UserUserEventId>, PagingAndSortingRepository<UserUserEvent, UserUserEventId> {

    List<UserUserEvent> findByEvent_EventId(long eventId);

    UserUserEvent findByUserUserEventIdAndUserUserEventActiveTrue(UserUserEventId userUserEventId);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE ORDER BY uUe.cost ASC")
    Page<UserUserEvent> findPageableUserUserEvents(@Param("eventId") long eventId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE")
    Long countUserUserEvents(@Param("eventId") long eventId);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE AND (uUe.sender.userName LIKE %:transactionActorName% OR uUe.receiver.userName LIKE %:transactionActorName%) ORDER BY uUe.cost ASC")
    Page<UserUserEvent> findPageableSearchUserUserEvents(@Param("eventId") long eventId, @Param("transactionActorName") String transactionActorName, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE AND (uUe.sender.userName LIKE %:transactionActorName% OR uUe.receiver.userName LIKE %:transactionActorName%)")
    Long countSearchUserUserEvents(@Param("eventId")long eventId, @Param("transactionActorName") String transactionActorName);

}
