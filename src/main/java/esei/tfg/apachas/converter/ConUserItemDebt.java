package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.UserItemDebt;
import esei.tfg.apachas.entity.id.UserItemId;
import esei.tfg.apachas.model.MUserItemDebt;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserItemDebt")
public class ConUserItemDebt {

    public List<MUserItemDebt> conUserItemDebtList(List<UserItemDebt> userItemDebtList) {
        List<MUserItemDebt> mUserItemDebtList = new ArrayList<>();
        for (UserItemDebt userItemDebt : userItemDebtList) {
            mUserItemDebtList.add(new MUserItemDebt(userItemDebt));
        }
        return mUserItemDebtList;
    }

    public MUserItemDebt conUserItemDebt(UserItemDebt userItemDebt) {
        return new MUserItemDebt(userItemDebt);
    }

    public UserItemDebt conMUserItemDebt(MUserItemDebt mUserItemDebt) {
        return new UserItemDebt(new UserItemId(mUserItemDebt.getItemId(), mUserItemDebt.getUserId()),mUserItemDebt.getDebtCost());
    }
}
