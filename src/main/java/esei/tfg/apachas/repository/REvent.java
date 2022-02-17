package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("REvent")
public interface REvent extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {

    Event findByEventId(long eventId);

    @Override
    Page<Event> findAll(Pageable pageable);
}
