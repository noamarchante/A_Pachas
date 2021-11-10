package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserGroupUser;
import esei.tfg.apachas.entity.id.UserGroupUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("RUserGroupUser")
public interface RUserGroupUser extends CrudRepository<UserGroupUser, UserGroupUserId>, PagingAndSortingRepository<UserGroupUser, UserGroupUserId> {
    UserGroupUser findByUserGroupUserId(UserGroupUserId userGroupUserId);
    List<UserGroupUser> findByUserGroupUserId_UserId(Long userId);
    List<UserGroupUser> findByUserGroupUserId_UserGroupId(Long userGroupId);

    Long countByUserGroupUserId_UserGroupIdAndUserGroupUserExitedIsNull(Long userGroupId);

    @Query("SELECT distinct uGu.user FROM UserGroupUser uGu where uGu.userGroupUserId.userGroupId = :userGroupId AND uGu.userGroupUserExited IS NULL ORDER BY uGu.user.userLogin ASC")
    Page<User> findByUserGroupUserId_UserGroupIdAAndUserGroupUserExitedIsNull(@Param("userGroupId") Long userGroupId, Pageable pageable);

    @Query("SELECT distinct uGu.user FROM UserGroupUser uGu where uGu.userGroupUserId.userGroupId = :userGroupId AND uGu.userGroupUserExited IS NULL ORDER BY uGu.user.userLogin ASC")
    List<User> findByUserGroupUserId_UserGroupIdAAndUserGroupUserExitedIsNull(@Param("userGroupId") Long userGroupId);



}
