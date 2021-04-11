package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.UserEventParticipate;
import esei.tfg.apachas.entity.id.UserEventId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUserEventParticipate")
public interface RUserEventParticipate extends CrudRepository<UserEventParticipate, UserEventId>, PagingAndSortingRepository<UserEventParticipate, UserEventId> {
    UserEventParticipate findByUserEventId(UserEventId userEventId);

    @Override
    Page<UserEventParticipate> findAll(Pageable pageable);
}
