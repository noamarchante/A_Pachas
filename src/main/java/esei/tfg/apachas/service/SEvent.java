package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConEvent;
import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.model.MEvent;
import esei.tfg.apachas.repository.REvent;
import esei.tfg.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SEvent")
public class SEvent {

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConEvent")
    private ConEvent conEvent;

    public synchronized boolean insert(MEvent mEvent) {
        Event event = conEvent.conMEvent(mEvent);
        Event existingEvent = rEvent.findByEventId(event.getEventId());
        User existingUser = rUser.findByUserId(event.getUser().getUserId());
        if (existingEvent != null || existingUser == null) {
            return false;
        } else {
            rEvent.save(event);
            return true;
        }
    }

    public synchronized boolean update(MEvent mEvent) {
        Event event = conEvent.conMEvent(mEvent);
        Event existingEvent = rEvent.findByEventId(event.getEventId());
        User existingUser = rUser.findByUserId(event.getUser().getUserId());
        if (existingEvent != null || existingUser != null) {
            rEvent.save(event);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(long eventId) {
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null) {
            rEvent.delete(existingEvent);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MEvent> selectAll() {
        List<Event> eventList = new ArrayList<>();
        rEvent.findAll().forEach(e -> eventList.add(e));
        return conEvent.conEventList(eventList);
    }

    public synchronized List<MEvent> selectPageable(Pageable pageable) {
        return conEvent.conEventList(rEvent.findAll(pageable).getContent());
    }

    public synchronized MEvent selectEventById(long eventId) {
        Event event = rEvent.findById(eventId).get();
        return conEvent.conEvent(event);
    }
}
