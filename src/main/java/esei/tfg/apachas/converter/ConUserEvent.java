package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.UserEvent;
import esei.tfg.apachas.entity.id.UserEventId;
import esei.tfg.apachas.model.MUserEvent;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserEvent")
public class ConUserEvent {

    public List<MUserEvent> conUserEventList(List<UserEvent> userEventList) {
        List<MUserEvent> mUserEventList = new ArrayList<>();
        for (UserEvent userEvent : userEventList) {
            mUserEventList.add(new MUserEvent(userEvent));
        }
        return mUserEventList;
    }

    public MUserEvent conUserEvent(UserEvent userEvent) {
        return new MUserEvent(userEvent);
    }

    public UserEvent conMUserEvent(MUserEvent mUserEvent) {
        return new UserEvent(new UserEventId(mUserEvent.getEventId(), mUserEvent.getUserId()), mUserEvent.getTotalExpense(), mUserEvent.getDebt(), mUserEvent.isAccept());
    }
}
