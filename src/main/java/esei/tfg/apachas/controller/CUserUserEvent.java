package esei.tfg.apachas.controller;

import esei.tfg.apachas.model.MUserUserEvent;
import esei.tfg.apachas.service.SUserEvent;
import esei.tfg.apachas.service.SUserUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usersUsersEvents")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.DELETE})

public class CUserUserEvent {

    @Autowired
    @Qualifier("SUserUserEvent")
    SUserUserEvent sUserUserEvent;

    @GetMapping("/pageable/{eventId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEvents(@PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEvents(eventId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }
    @GetMapping("/pageable/{transactionActorName}/{eventId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableSearchUserUserEvents(@PathVariable("transactionActorName") String transactionActorName, @PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUserUserEvent> userUserEventList = sUserUserEvent.selectPageableSearchUserUserEvents(transactionActorName, eventId,pageable);
        return new ResponseEntity<>(userUserEventList, HttpStatus.OK);
    }

    @GetMapping("/count/{transactionActorName}/{eventId}")
    public ResponseEntity<Long> countSearchUserUserEvents(@PathVariable("transactionActorName") String transactionActorName, @PathVariable("eventId") long eventId) {
        long userUserEventCount = sUserUserEvent.countSearchUserUserEvents(transactionActorName, eventId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/{eventId}")
    public ResponseEntity<Long> countEvents( @PathVariable("eventId") long eventId) {
        long userUserEventCount = sUserUserEvent.countUserUserEvents(eventId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUserUserEvent(@RequestBody @Valid long eventId) {
        boolean flag = sUserUserEvent.insertUserUserEvent(eventId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
