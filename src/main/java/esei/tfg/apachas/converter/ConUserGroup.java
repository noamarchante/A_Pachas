package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.UserGroup;
import esei.tfg.apachas.model.MUserGroup;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserGroup")
public class ConUserGroup {

    public List<MUserGroup> conUserGroupList(List<UserGroup> userGroupList) {
        List<MUserGroup> mUserGroupList = new ArrayList<>();
        for (UserGroup userGroup : userGroupList) {
            mUserGroupList.add(new MUserGroup(userGroup));
        }
        return mUserGroupList;
    }

    public MUserGroup conUserGroup(UserGroup userGroup) {
        return new MUserGroup(userGroup);
    }

    public UserGroup conMUserGroup(MUserGroup mUserGroup) {
        return new UserGroup(mUserGroup.getUserGroupId(), mUserGroup.getUserGroupName());
    }

}
