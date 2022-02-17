package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUser")
public interface RUser extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findByUserId(long userId);

    User findByUserLoginAndUserActiveTrue(String userLogin);

    Page<User> findUsersByRolesEqualsAndUserIdIsNotAndUserActiveTrueOrderByUserLoginAsc(String roles, long authId, Pageable pageable);

    Page<User> findUsersByUserLoginContainingAndUserIdIsNotAndRolesEqualsAndUserActiveTrueOrderByUserLoginAsc(String userLogin, long authId, String roles, Pageable pageable);

    Long countByRolesAndUserIdIsNotAndUserActiveTrue(String roles, long authId);

    Long countByRolesAndUserLoginContainingAndUserIdIsNotAndUserActiveTrue(String roles, String userLogin, long authId);
}
