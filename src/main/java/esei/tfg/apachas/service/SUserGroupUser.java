package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConUser;
import esei.tfg.apachas.converter.ConUserGroup;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.model.MUserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConUserGroupUser;
import esei.tfg.apachas.entity.*;
import esei.tfg.apachas.model.MUserGroupUser;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RUserGroup;
import esei.tfg.apachas.repository.RUserGroupUser;
import java.util.List;

@Service("SUserGroupUser")
public class SUserGroupUser {
    @Autowired
    @Qualifier("RUserGroupUser")
    private RUserGroupUser rUserGroupUser;

    @Autowired
    @Qualifier("RUserGroup")
    private RUserGroup rUserGroup;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUserGroupUser")
    private ConUserGroupUser conUserGroupUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    public synchronized boolean insertUserGroupUser(MUserGroupUser mUserGroupUser) {
        UserGroupUser userGroupUser = conUserGroupUser.conMUserGroupUser(mUserGroupUser);
        UserGroupUser existingUserGroupUser = rUserGroupUser.findByUserGroupUserId(userGroupUser.getUserGroupUserId());
        UserGroup existingUserGroup = rUserGroup.findByUserGroupId(userGroupUser.getUserGroupUserId().getUserGroupId());
        User existingUser = rUser.findByUserId(userGroupUser.getUserGroupUserId().getUserId());
        if (existingUserGroupUser != null || existingUserGroup == null || existingUser == null) {
            return false;
        } else {
            userGroupUser.setUserGroup(existingUserGroup);
            userGroupUser.setUser(existingUser);
            rUserGroupUser.save(userGroupUser);
            return true;
        }
    }

    public synchronized Long countUsersGroupsUsersByUserGroupId(long userGroupId){
        return rUserGroupUser.countByUserGroupUserId_UserGroupIdAndUserGroupUserExitedIsNull(userGroupId);
    }

    public synchronized List<MUser> selectPageableUserByUserGroupId(long userGroupId, Pageable pageable) {
        List<User> userList = rUserGroupUser.findByUserGroupUserId_UserGroupIdAAndUserGroupUserExitedIsNull(userGroupId, pageable).getContent();
        return conUser.conUserList(userList);
    }

    public synchronized List<MUser> selectUserByUserGroupId(long userGroupId) {
        List<User> userList = rUserGroupUser.findByUserGroupUserId_UserGroupIdAAndUserGroupUserExitedIsNull(userGroupId);
        return conUser.conUserList(userList);
    }

    public synchronized boolean update(MUserGroupUser mUserGroupUser) {
        UserGroupUser userGroupUser = conUserGroupUser.conMUserGroupUser(mUserGroupUser);
        UserGroupUser existingUserGroupUser = rUserGroupUser.findByUserGroupUserId(userGroupUser.getUserGroupUserId());
        UserGroup existingUserGroup = rUserGroup.findByUserGroupId(userGroupUser.getUserGroup().getUserGroupId());
        User existingUser = rUser.findByUserId(userGroupUser.getUser().getUserId());

        if (existingUserGroupUser != null || existingUserGroup != null || existingUser != null) {
            rUserGroupUser.save(userGroupUser);
            return true;
        } else {
            return false;
        }
    }
/*
    public synchronized boolean delete(UserGroupUserId userGroupUserId) {
        UserGroupUser existingUserGroupUser = rUserGroupUser.findByUserGroupUserId(userGroupUserId);
        if (existingUserGroupUser != null) {
            rUserGroupUser.delete(existingUserGroupUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MUserGroupUser> selectAll() {
        List<UserGroupUser> userGroupUserList = new ArrayList<>();
        rUserGroupUser.findAll().forEach(e -> userGroupUserList.add(e));
        return conUserGroupUser.conUserGroupUserList(userGroupUserList);
    }

    public synchronized List<MUserGroupUser> selectPageable(Pageable pageable) {
        return conUserGroupUser.conUserGroupUserList(rUserGroupUser.findAll(pageable).getContent());
    }

    public synchronized MUserGroupUser selectUserGroupUserById(UserGroupUserId userGroupUserId) {
        UserGroupUser userGroupUser = rUserGroupUser.findById(userGroupUserId).get();
        return conUserGroupUser.conUserGroupUser(userGroupUser);
    }*/
}
