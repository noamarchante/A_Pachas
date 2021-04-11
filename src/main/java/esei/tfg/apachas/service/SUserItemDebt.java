package esei.tfg.apachas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConUserItemDebt;
import esei.tfg.apachas.entity.Item;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserItemDebt;
import esei.tfg.apachas.entity.id.UserItemId;
import esei.tfg.apachas.model.MUserItemDebt;
import esei.tfg.apachas.repository.RItem;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RUserItemDebt;
import java.util.ArrayList;
import java.util.List;

@Service("SUserItemDebt")
public class SUserItemDebt {
    @Autowired
    @Qualifier("RUserItemDebt")
    private RUserItemDebt rUserItemDebt;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("RItem")
    private RItem rItem;

    @Autowired
    @Qualifier("ConUserItemDebt")
    private ConUserItemDebt conUserItemDebt;

    public synchronized boolean insert(MUserItemDebt mUserItemDebt) {
        UserItemDebt userItemDebt = conUserItemDebt.conMUserItemDebt(mUserItemDebt);
        UserItemDebt existingUserItemDebt = rUserItemDebt.findByUserItemId(userItemDebt.getUserItemId());
        User existingUser = rUser.findByUserId(userItemDebt.getUserItemId().getUserId());
        Item existingItem = rItem.findByItemId(userItemDebt.getUserItemId().getItemId());
        if (existingUserItemDebt != null || existingUser == null || existingItem == null) {
            return false;
        } else {
            userItemDebt.setUser(existingUser);
            userItemDebt.setItem(existingItem);
            rUserItemDebt.save(userItemDebt);
            return true;
        }
    }

    public synchronized boolean update(MUserItemDebt mUserItemDebt) {
        UserItemDebt userItemDebt = conUserItemDebt.conMUserItemDebt(mUserItemDebt);
        UserItemDebt existingUserItemDebt = rUserItemDebt.findByUserItemId(userItemDebt.getUserItemId());
        User existingUser = rUser.findByUserId(userItemDebt.getUserItemId().getUserId());
        Item existingItem = rItem.findByItemId(userItemDebt.getUserItemId().getItemId());
        if (existingUserItemDebt != null || existingUser != null || existingItem != null) {
            rUserItemDebt.save(userItemDebt);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(UserItemId userItemId) {
        UserItemDebt existingUserItemDebt = rUserItemDebt.findByUserItemId(userItemId);
        if (existingUserItemDebt != null) {
            rUserItemDebt.delete(existingUserItemDebt);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MUserItemDebt> selectAll() {
        List<UserItemDebt> userItemDebtList = new ArrayList<>();
        rUserItemDebt.findAll().forEach(e -> userItemDebtList.add(e));
        return conUserItemDebt.conUserItemDebtList(userItemDebtList);
    }

    public synchronized List<MUserItemDebt> selectPageable(Pageable pageable) {
        return conUserItemDebt.conUserItemDebtList(rUserItemDebt.findAll(pageable).getContent());
    }

    public synchronized MUserItemDebt selectUserItemDebtById(UserItemId userItemId) {
        UserItemDebt userItemDebt = rUserItemDebt.findById(userItemId).get();
        return conUserItemDebt.conUserItemDebt(userItemDebt);
    }
}
