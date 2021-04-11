package esei.tfg.apachas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConUserEventParticipate;
import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.entity.UserEventParticipate;
import esei.tfg.apachas.entity.id.UserEventId;
import esei.tfg.apachas.model.MUserEventParticipate;
import esei.tfg.apachas.repository.REvent;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RUserEventParticipate;

import java.util.ArrayList;
import java.util.List;

@Service("SUserEventParticipate")
public class SUserEventParticipate {

    @Autowired
    @Qualifier("RUserEventParticipate")
    private RUserEventParticipate rUserEventParticipate;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUserEventParticipate")
    private ConUserEventParticipate conUserEventParticipate;

    public synchronized boolean insert(MUserEventParticipate mUserEventParticipate) {
        UserEventParticipate userEventParticipate = conUserEventParticipate.conMUserEvent(mUserEventParticipate);
        UserEventParticipate existingUserEventParticipate = rUserEventParticipate.findByUserEventId(userEventParticipate.getUserEventId());
        Event existingEvent = rEvent.findByEventId(userEventParticipate.getUserEventId().getEventId());
        User existingUser = rUser.findByUserId(userEventParticipate.getUserEventId().getUserId());
        if (existingUserEventParticipate != null || existingEvent == null || existingUser == null) {
            return false;
        } else {
            userEventParticipate.setEvent(existingEvent);
            userEventParticipate.setUser(existingUser);
            rUserEventParticipate.save(userEventParticipate);
            return true;
        }
    }

    public synchronized boolean update(MUserEventParticipate mUserEventParticipate) {
        UserEventParticipate userEventParticipate = conUserEventParticipate.conMUserEvent(mUserEventParticipate);
        UserEventParticipate existingUserEventParticipate = rUserEventParticipate.findByUserEventId(userEventParticipate.getUserEventId());
        Event existingEvent = rEvent.findByEventId(userEventParticipate.getUserEventId().getEventId());
        User existingUser = rUser.findByUserId(userEventParticipate.getUserEventId().getUserId());
        if (existingUserEventParticipate != null || existingEvent != null || existingUser != null) {
            rUserEventParticipate.save(userEventParticipate);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(UserEventId userEventId) {
        UserEventParticipate existingUserEventParticipate = rUserEventParticipate.findByUserEventId(userEventId);
        if (existingUserEventParticipate != null) {
            rUserEventParticipate.delete(existingUserEventParticipate);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MUserEventParticipate> selectAll() {
        List<UserEventParticipate> userEventParticipateList = new ArrayList<>();
        rUserEventParticipate.findAll().forEach(e -> userEventParticipateList.add(e));
            return conUserEventParticipate.conUserEventList(userEventParticipateList);
    }

    public synchronized List<MUserEventParticipate> selectPageable(Pageable pageable) {
        return conUserEventParticipate.conUserEventList(rUserEventParticipate.findAll(pageable).getContent());
    }

    public synchronized MUserEventParticipate selectUserEventById(UserEventId userEventId) {
        UserEventParticipate userEventParticipate = rUserEventParticipate.findById(userEventId).get();
        return conUserEventParticipate.conUserEvent(userEventParticipate);
    }

}
