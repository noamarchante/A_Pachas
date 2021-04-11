package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUserGroup")
public interface RUserGroup extends CrudRepository<UserGroup, Long>, PagingAndSortingRepository<UserGroup, Long> {
    UserGroup findByUserGroupId(long userGroupId);

    @Override
    Page<UserGroup> findAll(Pageable pageable);
}
