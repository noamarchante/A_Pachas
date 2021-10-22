package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserUser;
import esei.tfg.apachas.entity.id.UserUserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("RUserUser")
public interface RUserUser extends CrudRepository<UserUser, UserUserId>, PagingAndSortingRepository<UserUser, UserUserId> {

    UserUser findByUserUserId(UserUserId userUserId);
    @Query("SELECT uU.user FROM userUser uU where uU.userUserId.friendId = :friendId and uU.status = true")
    List<User> findUserByUserUserId_FriendId(@Param("friendId") long friendId);

    @Query("SELECT uU.friend FROM userUser uU where uU.userUserId.userId = :userId and uU.status = true")
    List<User> findFriendByUserUserId_UserId(@Param("userId") long userId);
}
