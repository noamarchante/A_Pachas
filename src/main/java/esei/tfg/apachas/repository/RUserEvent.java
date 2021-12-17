package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserEvent;
import esei.tfg.apachas.entity.id.UserEventId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RUserEvent")
public interface RUserEvent extends CrudRepository<UserEvent, UserEventId>, PagingAndSortingRepository<UserEvent, UserEventId> {

    UserEvent findByUserEventId(UserEventId userEventId);

    List<UserEvent> findByUserEventId_EventId(long eventId);

    @Override
    Page<UserEvent> findAll(Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventName LIKE %:eventName% ORDER BY e.eventName ASC")
    Page<Event> findPageableSearchEvents(@Param("authId") long authId, @Param("eventName") String eventName, Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventName LIKE %:eventName% ORDER BY e.eventName ASC")
    Page<Event> findPageableSearchEventsWithFinished(@Param("authId") long authId, @Param("eventName") String eventName, Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE ORDER BY e.eventName ASC")
    Page<Event> findPageableEvents(@Param("authId") long authId, Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE ORDER BY e.eventName ASC")
    Page<Event> findPageableEventsWithFinished(@Param("authId") long authId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE")
    Long countEvents(@Param("authId") long authId);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE")
    Long countEventsWithFinished(@Param("authId") long authId);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventName LIKE %:eventName%")
    Long countSearchEvents(@Param("authId")long authId, @Param("eventName") String eventName);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventName LIKE %:eventName%")
    Long countSearchEventsWithFinished(@Param("authId")long authId, @Param("eventName") String eventName);
    
    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :userId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventId IN (SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE)")
    Long countMutualEvents(@Param("userId")long userId, @Param("authId") long authId);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :userId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventId IN (SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE) ORDER BY e.eventName ASC")
    Page<Event> findPageableMutualEvents(@Param("userId") long userId, @Param("authId") long authId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uE) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE")
    Long countPartakers(@Param("eventId") long eventId);

    @Query("SELECT DISTINCT uE.user FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE ORDER BY uE.user.userLogin ASC")
    Page<User> findPageablePartakers(@Param("eventId") long eventId, Pageable pageable);

    @Query("SELECT DISTINCT uE.user FROM event e, userEvent uE where e.eventId = uE.userEventId.eventId AND uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE ORDER BY uE.user.userLogin ASC")
    List<User> findPartakers(@Param("eventId") long eventId);

    UserEvent findUserEventByUserEventId_EventIdAndUserEventId_UserId(@Param("eventId") long eventId, @Param("authId") long authId);

}
