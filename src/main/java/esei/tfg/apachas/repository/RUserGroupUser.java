package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.UserGroupUser;
import esei.tfg.apachas.entity.id.UserGroupUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RUserGroupUser")
public interface RUserGroupUser extends CrudRepository<UserGroupUser, UserGroupUserId>, PagingAndSortingRepository<UserGroupUser, UserGroupUserId> {
    UserGroupUser findByUserGroupUserId(UserGroupUserId userGroupUserId);
    List<UserGroupUser> findByUserGroupUserId_UserId(Long userId);
    @Override
    Page<UserGroupUser> findAll(Pageable pageable);
}
