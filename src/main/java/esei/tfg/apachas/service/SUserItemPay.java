package esei.tfg.apachas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConUserItemPay;
import esei.tfg.apachas.entity.Item;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.id.UserItemId;
import esei.tfg.apachas.entity.UserItemPay;
import esei.tfg.apachas.model.MUserItemPay;
import esei.tfg.apachas.repository.RItem;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RUserItemPay;
import java.util.ArrayList;
import java.util.List;

@Service("SUserItemPay")
public class SUserItemPay {
    @Autowired
    @Qualifier("RUserItemPay")
    private RUserItemPay rUserItemPay;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("RItem")
    private RItem rItem;

    @Autowired
    @Qualifier("ConUserItemPay")
    private ConUserItemPay conUserItemPay;

    public synchronized boolean insert(MUserItemPay mUserItemPay) {
        UserItemPay userItemPay = conUserItemPay.conMUserItemPay(mUserItemPay);
        UserItemPay existingUserItemPay = rUserItemPay.findByUserItemId(userItemPay.getUserItemId());
        User existingUser = rUser.findByUserId(userItemPay.getUserItemId().getUserId());
        Item existingItem = rItem.findByItemId(userItemPay.getUserItemId().getItemId());
        if (existingUserItemPay != null || existingUser == null || existingItem == null) {
            return false;
        } else {
            userItemPay.setUser(existingUser);
            userItemPay.setItem(existingItem);
            rUserItemPay.save(userItemPay);
            return true;
        }
    }

    public synchronized boolean update(MUserItemPay mUserItemPay) {
        UserItemPay userItemPay = conUserItemPay.conMUserItemPay(mUserItemPay);
        UserItemPay existingUserItemPay = rUserItemPay.findByUserItemId(userItemPay.getUserItemId());
        User existingUser = rUser.findByUserId(userItemPay.getUserItemId().getUserId());
        Item existingItem = rItem.findByItemId(userItemPay.getUserItemId().getItemId());
        if (existingUserItemPay != null || existingUser != null || existingItem != null) {
            rUserItemPay.save(userItemPay);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(UserItemId userItemId) {
        UserItemPay existingUserItemPay = rUserItemPay.findByUserItemId(userItemId);
        if (existingUserItemPay != null) {
            rUserItemPay.delete(existingUserItemPay);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MUserItemPay> selectAll() {
        List<UserItemPay> userItemPayList = new ArrayList<>();
        rUserItemPay.findAll().forEach(e -> userItemPayList.add(e));
        return conUserItemPay.conUserItemPayList(userItemPayList);
    }

    public synchronized List<MUserItemPay> selectPageable(Pageable pageable) {
        return conUserItemPay.conUserItemPayList(rUserItemPay.findAll(pageable).getContent());
    }

    public synchronized MUserItemPay selectUserItemPayById(UserItemId userItemId) {
        UserItemPay userItemPay = rUserItemPay.findById(userItemId).get();
        return conUserItemPay.conUserItemPay(userItemPay);
    }
}
