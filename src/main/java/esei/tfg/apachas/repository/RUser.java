package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("RUser")
public interface RUser extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findByUserId(long userId);
    User findByUserLogin (String userLogin);
    Page<User> findUsersByRolesEqualsAndUserIdIsNotOrderByUserLoginAsc(String roles, long authId, Pageable pageable);
    Page<User> findUsersByUserLoginContainingAndUserIdIsNotAndRolesEqualsOrderByUserLoginAsc (String userLogin, long authId, String roles, Pageable pageable);
    Long countByRolesAndUserIdIsNot(String roles, long authId);
    Long countByRolesAndUserLoginContainingAndUserIdIsNot(String roles,String userLogin, long authId);

    @Query("SELECT COUNT(DISTINCT u) FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND (uU.userUserId.userId = :userId OR uU.userUserId.userId = :authId) AND uU.status = FALSE AND u.userId IN (SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND (uU.userUserId.friendId = :authId OR uU.userUserId.friendId = :userId) AND uU.status = FALSE)")
    Long countMutualFriends(@Param("userId")long userId, @Param("authId") long authId);

    @Query("SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND (uU.userUserId.userId = :userId OR uU.userUserId.userId = :authId) AND uU.status = FALSE AND u.userId IN (SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND (uU.userUserId.friendId = :authId OR uU.userUserId.friendId = :userId) AND uU.status = FALSE)")
    Page<User> findByMutualFriends(@Param("userId") long userId, @Param("authId") long authId, Pageable pageable);

}
