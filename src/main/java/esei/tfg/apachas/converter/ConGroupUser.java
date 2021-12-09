package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.GroupUser;
import esei.tfg.apachas.entity.id.GroupUserId;
import esei.tfg.apachas.model.MGroupUser;
import java.util.ArrayList;
import java.util.List;

@Component("ConGroupUser")
public class ConGroupUser {
    public List<MGroupUser> conGroupUserList(List<GroupUser> groupUserList) {
        List<MGroupUser> mGroupUserList = new ArrayList<>();
        for (GroupUser groupUser : groupUserList) {
            mGroupUserList.add(new MGroupUser(groupUser));
        }
        return mGroupUserList;
    }

    public MGroupUser conGroupUser(GroupUser groupUser) {
        return new MGroupUser(groupUser);
    }

    public GroupUser conMGroupUser(MGroupUser mGroupUser) {
        return new GroupUser(new GroupUserId(mGroupUser.getGroupId(), mGroupUser.getUserId()));
    }
}
