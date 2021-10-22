package esei.tfg.apachas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConUserGroup;
import esei.tfg.apachas.entity.UserGroup;
import esei.tfg.apachas.model.MUserGroup;
import esei.tfg.apachas.repository.RUserGroup;

import java.util.Date;
import java.util.List;

@Service("SUserGroup")
public class SUserGroup {
    @Autowired
    @Qualifier("RUserGroup")
    private RUserGroup rUserGroup;

    @Autowired
    @Qualifier("ConUserGroup")
    private ConUserGroup conUserGroup;

    public synchronized Long insertUserGroup(MUserGroup mUserGroup) {
        UserGroup userGroup = conUserGroup.conMUserGroup(mUserGroup);
        UserGroup existingUserGroup = rUserGroup.findByUserGroupId(userGroup.getUserGroupId());
        if (existingUserGroup != null) {
            return 0L;
        } else {
            return rUserGroup.save(userGroup).getUserGroupId();
        }
    }

    public synchronized Long countUsersGroups(long authId){
        return rUserGroup.countByUser_UserIdAndUserGroupRemovalIsNull(authId);
    }

    public synchronized Long countSearchUsersGroups( long authId, String userGroupName){
        return rUserGroup.countByUser_UserIdAndUserGroupNameContainingAndUserGroupRemovalIsNull(authId, userGroupName);
    }

    public synchronized List<MUserGroup> selectUserGroupByUserId(Long userId, Pageable pageable) {
        List<UserGroup> userGroupList = rUserGroup.findByUser_UserId(userId, pageable).getContent();
        return conUserGroup.conUserGroupList(userGroupList);
    }

    public synchronized List<MUserGroup> selectPageableUserGroupByUserGroupName(long authId, String userGroupName, Pageable pageable) {
        return conUserGroup.conUserGroupList(rUserGroup.findByUser_UserIdAndUserGroupNameContaining(authId, userGroupName, pageable).getContent());
    }


    public synchronized boolean delete(MUserGroup mUserGroup) {
        UserGroup userGroup = conUserGroup.conMUserGroup(mUserGroup);
        UserGroup existingUserGroup = rUserGroup.findByUserGroupId(userGroup.getUserGroupId());
        if (existingUserGroup != null) {
            userGroup.setUserGroupRemoval(new Date());
            rUserGroup.save(userGroup);
            return true;
        } else {
            return false;
        }
    }
/*
    public synchronized boolean delete(long userGroupId) {
        UserGroup existingUserGroup = rUserGroup.findByUserGroupId(userGroupId);
        if (existingUserGroup != null) {
            rUserGroup.delete(existingUserGroup);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MUserGroup> selectAll() {
        List<UserGroup> userGroupList = new ArrayList<>();
        rUserGroup.findAll().forEach(e -> userGroupList.add(e));
        return conUserGroup.conUserGroupList(userGroupList);
    }

    public synchronized List<MUserGroup> selectPageable(Pageable pageable) {
        return conUserGroup.conUserGroupList(rUserGroup.findAll(pageable).getContent());
    }

    public synchronized MUserGroup selectUserGroupById(long userGroupId) {
        UserGroup userGroup = rUserGroup.findById(userGroupId).get();
        return conUserGroup.conUserGroup(userGroup);
    }*/
}
