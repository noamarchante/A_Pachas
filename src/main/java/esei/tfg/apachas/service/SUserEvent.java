package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConEvent;
import esei.tfg.apachas.converter.ConUser;
import esei.tfg.apachas.entity.*;
import esei.tfg.apachas.entity.id.GroupUserId;
import esei.tfg.apachas.model.MEvent;
import esei.tfg.apachas.model.MGroupUser;
import esei.tfg.apachas.model.MUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConUserEvent;
import esei.tfg.apachas.entity.id.UserEventId;
import esei.tfg.apachas.model.MUserEvent;
import esei.tfg.apachas.repository.REvent;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RUserEvent;

import java.sql.Timestamp;
import java.util.List;

@Service("SUserEvent")
public class SUserEvent {

    @Autowired
    @Qualifier("RUserEvent")
    private RUserEvent rUserEvent;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUserEvent")
    private ConUserEvent conUserEvent;

    @Autowired
    @Qualifier("ConEvent")
    private ConEvent conEvent;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    public synchronized boolean insertUserEvent(MUserEvent mUserEvent) {
        UserEvent userEvent = conUserEvent.conMUserEvent(mUserEvent);
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(userEvent.getUserEventId());
        Event existingEvent = rEvent.findByEventId(userEvent.getUserEventId().getEventId());
        User existingUser = rUser.findByUserId(userEvent.getUserEventId().getUserId());
        if (existingUserEvent != null || existingEvent == null || existingUser == null) {
            return false;
        } else {
            userEvent.setAccept(false);
            userEvent.setUserEventActive(true);
            userEvent.setUserEventRemoval(null);
            userEvent.setUserEventCreation(new Timestamp(System.currentTimeMillis()));
            userEvent.setEvent(existingEvent);
            userEvent.setUser(existingUser);
            rUserEvent.save(userEvent);
            return true;
        }
    }

    public synchronized boolean updateUserEvent(Long eventId, List<Long> userIdList) {
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null){
            List<UserEvent> existingUserEventList = rUserEvent.findByUserEventId_EventId(eventId);
            existingUserEventList.forEach(userEvent -> {
                if (!userIdList.contains(userEvent.getUserEventId().getUserId())){
                    deleteUserEvent(userEvent.getUserEventId().getEventId(), userEvent.getUserEventId().getUserId());
                }
                userIdList.remove(userEvent.getUserEventId().getUserId());
            });

            userIdList.forEach(userId -> {
                insertUserEvent(new MUserEvent(new UserEvent(new UserEventId(eventId, userId), 0.0, false)));
            });
            return true;
        }else{
            return false;
        }
    }

    public synchronized boolean updateStatus(Long eventId, long authId) {
        Event existingEvent = rEvent.findByEventId(eventId);
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(new UserEventId(eventId, authId));
        User existingUser = rUser.findByUserId(authId);

        if (existingEvent != null || existingUser != null || existingUserEvent != null){
            existingUserEvent.setAccept(true);
            rUserEvent.save(existingUserEvent);
            return true;
        }else{
            return false;
        }
    }

    public synchronized boolean deleteUserEvent(long eventId, long userId) {
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(new UserEventId(eventId, userId));
        Event existingEvent = rEvent.findByEventId(eventId);
        User existingUser = rUser.findByUserId(userId);
        if (existingUserEvent != null || existingEvent != null || existingUser != null) {
            existingUserEvent.setUserEventActive(false);
            existingUserEvent.setUserEventRemoval(new Timestamp(System.currentTimeMillis()));
            rUserEvent.save(existingUserEvent);
            return true;
        } else {
            return false;
        }
    }

    public synchronized Long countEvents(long authId){
        return rUserEvent.countEvents(authId);
    }

    public synchronized Long countSearchEvents(String eventName, long authId){
        return rUserEvent.countSearchEvents(authId, eventName);
    }

    public synchronized Long countMutualEvents( long userId, long authId){
        return rUserEvent.countMutualEvents(userId, authId);
    }

    public synchronized Long countPartakers(long eventId){
        return rUserEvent.countPartakers(eventId);
    }

    public synchronized List<MEvent> selectPageableEvents(Long userId, Pageable pageable) {
        List<Event> eventList = rUserEvent.findPageableEvents(userId, pageable).getContent();
        return conEvent.conEventList(eventList);
    }

    public synchronized List<MEvent> selectPageableSearchEvents(String eventName, long authId, Pageable pageable) {
        return conEvent.conEventList(rUserEvent.findPageableSearchEvents(authId, eventName, pageable).getContent());
    }

    public synchronized List<MEvent> selectPageableMutualEvents(long userId, long authId, Pageable pageable) {
        return conEvent.conEventList(rUserEvent.findPageableMutualEvents(userId, authId, pageable).getContent());
    }

    public synchronized List<MUser> selectPageablePartakers(long eventId, Pageable pageable) {
        return conUser.conUserList(rUserEvent.findPageablePartakers(eventId, pageable).getContent());
    }

    public synchronized List<MUser> selectPartakers(long eventId) {
        List<User> userList = rUserEvent.findPartakers(eventId);
        return conUser.conUserList(userList);
    }

    public synchronized MUserEvent selectUserEvent(long eventId, long authId) {
        return conUserEvent.conUserEvent(rUserEvent.findUserEventByUserEventId_EventIdAndUserEventId_UserId(eventId, authId));
    }
}