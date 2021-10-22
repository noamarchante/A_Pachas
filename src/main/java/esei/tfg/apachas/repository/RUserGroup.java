package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("RUserGroup")
public interface RUserGroup extends CrudRepository<UserGroup, Long>, PagingAndSortingRepository<UserGroup, Long> {
    UserGroup findByUserGroupId(long userGroupId);

    @Query("SELECT distinct uG FROM userGroup uG, UserGroupUser uGu where uG.userGroupId = uGu.userGroupUserId.userGroupId AND (uGu.userGroupUserId.userId = :userId OR uG.user.userId = :userId) AND uGu.userGroupUserExited IS NULL AND uG.userGroupRemoval IS NULL AND uG.userGroupName LIKE %:userGroupName% ORDER BY uG.userGroupName ASC")
    Page<UserGroup> findByUser_UserIdAndUserGroupNameContaining(@Param("userId") long userId, @Param("userGroupName") String userGroupName, Pageable pageable);

    @Query("SELECT distinct uG FROM userGroup uG, UserGroupUser uGu where uG.userGroupId = uGu.userGroupUserId.userGroupId AND (uGu.userGroupUserId.userId = :userId OR uG.user.userId = :userId) AND uGu.userGroupUserExited IS NULL AND uG.userGroupRemoval IS NULL ORDER BY uG.userGroupName ASC")
    Page<UserGroup> findByUser_UserId(@Param("userId") long userId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uG) FROM userGroup uG, UserGroupUser uGu where uG.userGroupId = uGu.userGroupUserId.userGroupId AND (uGu.userGroupUserId.userId = :userId OR uG.user.userId = :userId) AND uGu.userGroupUserExited IS NULL AND uG.userGroupRemoval IS NULL")
    Long countByUser_UserIdAndUserGroupRemovalIsNull(@Param("userId") long userId);

    @Query("SELECT COUNT(DISTINCT uG) FROM userGroup uG, UserGroupUser uGu where uG.userGroupId = uGu.userGroupUserId.userGroupId AND (uGu.userGroupUserId.userId = :userId OR uG.user.userId = :userId) AND uGu.userGroupUserExited IS NULL AND uG.userGroupRemoval IS NULL AND uG.userGroupName LIKE %:userGroupName%")
    Long countByUser_UserIdAndUserGroupNameContainingAndUserGroupRemovalIsNull(@Param("userId")long userId, @Param("userGroupName") String userGroupName);
}
