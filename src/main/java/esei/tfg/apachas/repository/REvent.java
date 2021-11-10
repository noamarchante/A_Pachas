package esei.tfg.apachas.repository;


import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("REvent")
public interface REvent extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {

    Event findByEventId(long eventId);

    @Override
    Page<Event> findAll(Pageable pageable);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEventParticipate uEp WHERE e.eventId = uEp.userEventId.eventId AND uEp.userEventId.userId = :userId AND uEp.accept = FALSE AND e.eventState = TRUE AND e.eventId IN (SELECT DISTINCT e FROM event e, userEventParticipate uEp WHERE e.eventId = uEp.userEventId.eventId AND uEp.userEventId.userId =:authId AND uEp.accept = FALSE AND e.eventState = TRUE)")
    Long countMutualEvents(@Param("userId")long userId, @Param("authId") long authId);

    @Query("SELECT DISTINCT uG FROM userGroup uG, UserGroupUser uGu WHERE uG.userGroupId = uGu.userGroupUserId.userGroupId AND uGu.userGroupUserId.userId = :userId AND uGu.userGroupUserExited IS NULL AND uG.userGroupRemoval IS NULL AND uG.userGroupId IN (SELECT DISTINCT uG FROM userGroup uG, UserGroupUser uGu WHERE uG.userGroupId = uGu.userGroupUserId.userGroupId AND uGu.userGroupUserId.userId =:authId AND uGu.userGroupUserExited IS NULL AND uG.userGroupRemoval IS NULL)")
    Page<Event> findByMutualEvents(@Param("userId") long userId, @Param("authId") long authId, Pageable pageable);

}
