package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.id.UserItemId;
import esei.tfg.apachas.entity.UserItemPay;
import esei.tfg.apachas.model.MUserItemPay;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserItemPay")
public class ConUserItemPay {
    public List<MUserItemPay> conUserItemPayList(List<UserItemPay> userItemPayList) {
        List<MUserItemPay> mUserItemPayList = new ArrayList<>();
        for (UserItemPay userItemPay : userItemPayList) {
            mUserItemPayList.add(new MUserItemPay(userItemPay));
        }
        return mUserItemPayList;
    }

    public MUserItemPay conUserItemPay(UserItemPay userItemPay) {
        return new MUserItemPay(userItemPay);
    }

    public UserItemPay conMUserItemPay(MUserItemPay mUserItemPay) {
        return new UserItemPay(new UserItemId(mUserItemPay.getItemId(), mUserItemPay.getUserId()), mUserItemPay.getPayCost());
    }
}
