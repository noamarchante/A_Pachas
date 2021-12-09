package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConEvent;
import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.Group;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.model.MEvent;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.repository.REvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service("SEvent")
public class SEvent {

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("ConEvent")
    private ConEvent conEvent;

    public synchronized Long insertEvent(MEvent mEvent) {
        Event event = conEvent.conMEvent(mEvent);
        Event existingEvent = rEvent.findByEventId(event.getEventId());
        if (existingEvent != null) {
            return 0L;
        } else {
            event.setEventOpen(true);
            event.setEventActive(true);
            event.setEventCreation(new Timestamp(System.currentTimeMillis()));
            event.setEventRemoval(null);
            return rEvent.save(event).getEventId();
        }
    }

    public synchronized boolean updateEvent(MEvent mEvent) {
        Event event = conEvent.conMEvent(mEvent);
        Event existingEvent = rEvent.findByEventId(event.getEventId());
        if (existingEvent != null) {
            rEvent.save(event);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean deleteEvent(long eventId) {
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null) {
            existingEvent.setEventActive(false);
            existingEvent.setEventRemoval(new Timestamp(System.currentTimeMillis()));
            rEvent.save(existingEvent);
            return true;
        } else {
            return false;
        }
    }
}
