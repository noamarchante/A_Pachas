package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserUser;
import esei.tfg.apachas.entity.id.UserUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("RUserUser")
public interface RUserUser extends CrudRepository<UserUser, UserUserId>, PagingAndSortingRepository<UserUser, UserUserId> {

    UserUser findByUserUserId(UserUserId userUserId);

    UserUser findByUserUserIdAndUserUserActiveTrue(UserUserId userUserId);

    @Query("SELECT uU.user FROM userUser uU WHERE uU.userUserId.friendId = :friendId AND uU.userUserActive = TRUE")
    List<User> findFriendsByFriendId(@Param("friendId") long friendId);

    @Query("SELECT uU.friend FROM userUser uU WHERE uU.userUserId.userId = :userId AND uU.userUserActive = TRUE")
    List<User> findFriendsByUserId(@Param("userId") long userId);

    @Query("SELECT COUNT(DISTINCT u) FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.userId = :userId AND uU.userUserActive = TRUE AND uU.accept = TRUE AND u.userActive = TRUE AND u.userId IN (SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.friendId = :authId AND uU.accept = TRUE AND uU.userUserActive = TRUE AND u.userActive = TRUE)")
    Long countMutualFriends(@Param("userId")long userId, @Param("authId") long authId);

    @Query("SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.userId = :userId AND uU.userUserActive = TRUE AND uU.accept = TRUE AND u.userActive = TRUE AND u.userId IN (SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.friendId = :authId AND uU.accept = TRUE AND uU.userUserActive = TRUE AND u.userActive = TRUE) ORDER BY u.userLogin ASC")
    Page<User> findPageableMutualFriends(@Param("userId") long userId, @Param("authId") long authId, Pageable pageable);

}
