package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConUser;
import esei.tfg.apachas.model.MUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConUserUser;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserUser;
import esei.tfg.apachas.entity.id.UserUserId;
import esei.tfg.apachas.model.MUserUser;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RUserUser;
import java.util.List;

@Service("SUserUser")
public class SUserUser {
    @Autowired
    @Qualifier("RUserUser")
    private RUserUser rUserUser;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUserUser")
    private ConUserUser conUserUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    public synchronized boolean insertUserUser(MUserUser mUserUser) {
        UserUser userUser = conUserUser.conMUserUser(mUserUser);
        UserUser existingUserUser = rUserUser.findByUserUserId(userUser.getUserUserId());
        User existingUser = rUser.findByUserId(userUser.getUserUserId().getUserId());
        User existingFriend = rUser.findByUserId(userUser.getUserUserId().getFriendId());
        if (existingUserUser != null || existingUser == null || existingFriend == null) {
            return false;
        } else {
            userUser.setUser(existingUser);
            userUser.setFriend(existingFriend);
            rUserUser.save(userUser);
            return true;
        }
    }

    public synchronized boolean updateUserUser(MUserUser mUserUser) {
        UserUser userUser = conUserUser.conMUserUser(mUserUser);
        UserUser existingUserUser = rUserUser.findByUserUserId(userUser.getUserUserId());
        User existingUser = rUser.findByUserId(userUser.getUserUserId().getUserId());
        User existingFriend = rUser.findByUserId(userUser.getUserUserId().getFriendId());

        if (existingUserUser != null || existingUser != null || existingFriend != null) {
            rUserUser.save(userUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean deleteUserUser(UserUserId userUserId) {
        UserUser existingUserUser = rUserUser.findByUserUserId(userUserId);
        if (existingUserUser != null) {
            rUserUser.delete(existingUserUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized MUserUser selectUserUserById(UserUserId userUserId) {
        UserUser userUser = rUserUser.findById(userUserId).get();
        return conUserUser.conUserUser(userUser);
    }

    public synchronized List<MUser> selectFriendsByFriendId(long friendId) {
        List<User> userList = rUserUser.findUserByUserUserId_FriendId(friendId);
        return conUser.conUserList(userList);
    }

    public synchronized List<MUser> selectFriendsByUserId(long userId) {
        List<User> userList = rUserUser.findFriendByUserUserId_UserId(userId);
        return conUser.conUserList(userList);
    }

    /*public synchronized Long countByAuthUser(Long authId) {
        return rUserUser.countByUserUserId_FriendId(authId);
    }

    public synchronized List<MUserUser> selectAll() {
        List<UserUser> userUserList = new ArrayList<>();
        rUserUser.findAll().forEach(e -> userUserList.add(e));
        return conUserUser.conUserUserList(userUserList);
    }

    public synchronized List<MUserUser> selectAllByAuthUser(Long authId) {
        List<UserUser> userUserList = new ArrayList<>();
        rUserUser.findByUserUserId_FriendIdAAndStatus(authId,false).forEach(e -> userUserList.add(e));
        return conUserUser.conUserUserList(userUserList);
    }

    public synchronized List<MUserUser> selectPageable(Pageable pageable) {
        return conUserUser.conUserUserList(rUserUser.findAll(pageable).getContent());
    }*/
}
