package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.UserUser;
import esei.tfg.apachas.entity.id.UserUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUserUser")
public interface RUserUser extends CrudRepository<UserUser, UserUserId>, PagingAndSortingRepository<UserUser, UserUserId> {

    UserUser findByUserUserId(UserUserId userUserId);

    @Override
    Page<UserUser> findAll(Pageable pageable);
}
