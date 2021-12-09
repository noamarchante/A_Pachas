package esei.tfg.apachas.controller;

import esei.tfg.apachas.entity.id.GroupUserId;
import esei.tfg.apachas.entity.id.UserEventId;
import esei.tfg.apachas.model.MEvent;
import esei.tfg.apachas.model.MGroupUser;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.model.MUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import esei.tfg.apachas.service.SUserEvent;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usersEvents")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserEvent {

    @Autowired
    @Qualifier("SUserEvent")
    SUserEvent sUserEvent;

    @PostMapping
    public ResponseEntity<Void> createUserEvent(@RequestBody @Valid MUserEvent mUserEvent, UriComponentsBuilder builder) {
        boolean flag = sUserEvent.insertUserEvent(mUserEvent);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/usersEvents/{userEventId}").buildAndExpand(new UserEventId(mUserEvent.getEventId(), mUserEvent.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Void> editUserEvent(@PathVariable("eventId") long eventId, @RequestBody @Valid List<Long> userIdList) {
        boolean flag = sUserEvent.updateUserEvent(eventId, userIdList);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/status/{eventId}")
    public ResponseEntity<Void> editStatus (@PathVariable("eventId") long eventId, @RequestBody @Valid long authId) {
        boolean flag = sUserEvent.updateStatus(eventId, authId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{eventId}/{userId}")
    public ResponseEntity<Void> deleteGroupUser(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        boolean flag = sUserEvent.deleteUserEvent(eventId, userId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/count/mutual/{userId}/{authId}")
    public ResponseEntity<Long> countMutualEvents(@PathVariable("userId") long userId, @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countMutualEvents(userId,authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/{authId}")
    public ResponseEntity<Long> countEvents( @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countEvents(authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/{eventName}/{authId}")
    public ResponseEntity<Long> countSearchEvents(@PathVariable("eventName") String eventName, @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countSearchEvents(eventName, authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/partakers/{eventId}")
    public ResponseEntity<Long> countPartakers( @PathVariable("eventId") long eventId) {
        long eventCount = sUserEvent.countPartakers(eventId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }


    @GetMapping("/pageable/mutual/{userId}/{authId}")
    public ResponseEntity<List<MEvent>> getPageableMutualEvents(@PathVariable("userId") long userId, @PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> eventList = sUserEvent.selectPageableMutualEvents(userId, authId,pageable);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{eventName}/{authId}")
    public ResponseEntity<List<MEvent>> getPageableSearchEvents(@PathVariable("eventName") String eventName, @PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> eventList = sUserEvent.selectPageableSearchEvents(eventName, authId,pageable);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{authId}")
    public ResponseEntity<List<MEvent>> getPageableEvents(@PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> mEventList = sUserEvent.selectPageableEvents(authId, pageable);
        return new ResponseEntity<>(mEventList, HttpStatus.OK);
    }



    @GetMapping("/pageable/partakers/{eventId}")
    public ResponseEntity<List<MUser>> getPageablePartakers(@PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUser> mUserList = sUserEvent.selectPageablePartakers(eventId, pageable);
        return new ResponseEntity<>(mUserList, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<MUser>> getPartakers(@PathVariable("eventId") long eventId) {
        try {
            List<MUser> mUser = sUserEvent.selectPartakers(eventId);
            return new ResponseEntity<>(mUser, HttpStatus.OK);
        }catch ( NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/{eventId}/{authId}")
    public ResponseEntity<MUserEvent> getUserEvent(@PathVariable("eventId") long eventId, @PathVariable("authId") long authId) {
        try {
            MUserEvent mUserEvent = sUserEvent.selectUserEvent(eventId, authId);
            return new ResponseEntity<>(mUserEvent, HttpStatus.OK);
        }catch ( NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
    /*
    @DeleteMapping("/userEventParticipateDelete/{eventId}/{userId}")
    public ResponseEntity<Void> deleteUserEventParticipate(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        boolean flag = sUserEvent.delete(new UserEventId(eventId,userId));
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }*/
}
