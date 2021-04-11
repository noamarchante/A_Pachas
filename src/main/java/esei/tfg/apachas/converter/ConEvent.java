package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.model.MEvent;
import java.util.ArrayList;
import java.util.List;

@Component("ConEvent")
public class ConEvent {
    public List<MEvent> conEventList(List<Event> eventList) {
        List<MEvent> mEventList = new ArrayList<>();
        for (Event event : eventList) {
            mEventList.add(new MEvent(event));
        }
        return mEventList;
    }

    public MEvent conEvent(Event event) {
        return new MEvent(event);
    }

    public Event conMEvent(MEvent mEvent) {
        return new Event(mEvent.getEventId(), mEvent.getEventName(), mEvent.getEventDescription(), mEvent.getEventStartDate(), mEvent.getEventEndDate(), mEvent.getEventLocation(), mEvent.getEventPhoto(), mEvent.getEventState(), mEvent.getEventFinalPrice(), new User(mEvent.getUserId()));
    }

}
