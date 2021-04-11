package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.UserEventParticipate;
import esei.tfg.apachas.entity.id.UserEventId;
import esei.tfg.apachas.model.MUserEventParticipate;

import java.util.ArrayList;
import java.util.List;

@Component("ConUserEventParticipate")
public class ConUserEventParticipate {

    public List<MUserEventParticipate> conUserEventList(List<UserEventParticipate> userEventParticipateList) {
        List<MUserEventParticipate> mUserEventParticipateList = new ArrayList<>();
        for (UserEventParticipate userEventParticipate : userEventParticipateList) {
            mUserEventParticipateList.add(new MUserEventParticipate(userEventParticipate));
        }
        return mUserEventParticipateList;
    }

    public MUserEventParticipate conUserEvent(UserEventParticipate userEventParticipate) {
        return new MUserEventParticipate(userEventParticipate);
    }

    public UserEventParticipate conMUserEvent(MUserEventParticipate mUserEventParticipate) {
        return new UserEventParticipate(new UserEventId(mUserEventParticipate.getEventId(), mUserEventParticipate.getUserId()), mUserEventParticipate.getAccept(), mUserEventParticipate.getTotalDebt());
    }
}
