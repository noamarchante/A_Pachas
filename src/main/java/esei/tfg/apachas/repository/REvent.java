package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("REvent")
public interface REvent extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {

    @Query("SELECT DISTINCT uE FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.debt > 0 ORDER BY uE.debt ASC")
    List<UserEvent> findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtAsc(@Param("eventId") long eventId);

    Event findByEventId(long eventId);

    @Override
    Page<Event> findAll(Pageable pageable);


}
