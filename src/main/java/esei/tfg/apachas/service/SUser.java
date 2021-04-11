package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConUser;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("SUser")
public class SUser {

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;


    public synchronized boolean insert(MUser mUser) {
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        if (existingUser != null) {
            return false;
        } else {
            rUser.save(user);
            return true;
        }
    }

    public synchronized boolean update(MUser mUser) {
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        if (existingUser != null) {
            rUser.save(user);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(long userId) {
        User existingUser = rUser.findByUserId(userId);
        if (existingUser != null) {
            rUser.delete(existingUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MUser> selectAll() {
        List<User> userList = new ArrayList<>();
        rUser.findAll().forEach(e -> userList.add(e));
        return conUser.conUserList(userList);
    }

    public synchronized List<MUser> selectPageable(Pageable pageable) {
        return conUser.conUserList(rUser.findAll(pageable).getContent());
    }

    public synchronized MUser selectById(long userId) {
        User user = rUser.findById(userId).get();
        return conUser.conUser(user);
    }
}
