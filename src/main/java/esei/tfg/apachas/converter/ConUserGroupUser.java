package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.UserGroupUser;
import esei.tfg.apachas.entity.id.UserGroupUserId;
import esei.tfg.apachas.model.MUserGroupUser;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserGroupUser")
public class ConUserGroupUser {
    public List<MUserGroupUser> conUserGroupUserList(List<UserGroupUser> userGroupUserList) {
        List<MUserGroupUser> mUserGroupUserList = new ArrayList<>();
        for (UserGroupUser userGroupUser : userGroupUserList) {
            mUserGroupUserList.add(new MUserGroupUser(userGroupUser));
        }
        return mUserGroupUserList;
    }

    public MUserGroupUser conUserGroupUser(UserGroupUser userGroupUser) {
        return new MUserGroupUser(userGroupUser);
    }

    public UserGroupUser conMUserGroupUser(MUserGroupUser mUserGroupUser) {
        return new UserGroupUser(new UserGroupUserId(mUserGroupUser.getUserGroupId(), mUserGroupUser.getUserId()), mUserGroupUser.getUserGroupUserExited());
    }
}
