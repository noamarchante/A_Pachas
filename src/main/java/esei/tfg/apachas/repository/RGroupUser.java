package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.Group;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.GroupUser;
import esei.tfg.apachas.entity.id.GroupUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("RGroupUser")
public interface RGroupUser extends CrudRepository<GroupUser, GroupUserId>, PagingAndSortingRepository<GroupUser, GroupUserId> {

    GroupUser findByGroupUserId(GroupUserId groupUserId);

    List<GroupUser> findByGroupUserId_UserId(Long userId);

    List<GroupUser> findByGroupUserId_GroupId(Long groupId);

    Long countByGroupUserId_GroupIdAndGroupUserActiveTrue(Long groupId);

    @Query("SELECT DISTINCT gU.user FROM groupUser gU WHERE gU.groupUserId.groupId = :groupId AND gU.groupUserActive = TRUE ORDER BY gU.user.userLogin ASC")
    Page<User> findPageableMembers(@Param("groupId") Long groupId, Pageable pageable);

    @Query("SELECT DISTINCT gU.user FROM groupUser gU WHERE gU.groupUserId.groupId = :groupId AND gU.groupUserActive = TRUE ORDER BY gU.user.userLogin ASC")
    List<User> findMembers(@Param("groupId") Long groupId);

    @Query("SELECT DISTINCT g FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId = :authId AND gU.groupUserActive = TRUE AND g.groupActive = TRUE AND g.groupName LIKE %:groupName% ORDER BY g.groupName ASC")
    Page<Group> findPageableSearchGroups(@Param("authId") long authId, @Param("groupName") String groupName, Pageable pageable);

    @Query("SELECT DISTINCT g FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId = :authId AND gU.groupUserActive = TRUE  AND g.groupActive = TRUE ORDER BY g.groupName ASC")
    Page<Group> findPageableGroups(@Param("authId") long authId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT g) FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId = :authId AND gU.groupUserActive = TRUE  AND g.groupActive = TRUE")
    Long countGroups(@Param("authId") long authId);

    @Query("SELECT COUNT(DISTINCT g) FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId = :authId  AND gU.groupUserActive = TRUE  AND g.groupActive = TRUE AND g.groupName LIKE %:groupName%")
    Long countSearchGroups(@Param("authId")long authId, @Param("groupName") String groupName);

    @Query("SELECT COUNT(DISTINCT g) FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId = :userId AND gU.groupUserActive = TRUE AND g.groupActive = TRUE AND g.groupId IN (SELECT DISTINCT g FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId =:authId AND gU.groupUserActive = TRUE AND g.groupActive = TRUE)")
    Long countMutualGroups(@Param("userId")long userId, @Param("authId") long authId);

    @Query("SELECT DISTINCT g FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId = :userId AND gU.groupUserActive = TRUE AND g.groupActive = TRUE AND g.groupId IN (SELECT DISTINCT g FROM uGroup g, groupUser gU WHERE g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId =:authId AND gU.groupUserActive = TRUE AND g.groupActive = TRUE)")
    Page<Group> findPageableMutualGroups(@Param("userId") long userId, @Param("authId") long authId, Pageable pageable);

    @Query("SELECT DISTINCT g FROM uGroup g, groupUser gU where g.groupId = gU.groupUserId.groupId AND gU.groupUserId.userId= :authId AND gU.groupUserActive = TRUE AND g.groupActive = TRUE ORDER BY g.groupName ASC")
    List<Group> getGroups(@Param("authId") long authId);
}
