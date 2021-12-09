package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import esei.tfg.apachas.model.MEvent;
import esei.tfg.apachas.service.SEvent;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CEvent {

    @Autowired
    @Qualifier("SEvent")
    SEvent sEvent;

    @PostMapping
    public ResponseEntity<Long> createEvent(@RequestBody @Valid MEvent mEvent, UriComponentsBuilder builder) {
        Long eventId = sEvent.insertEvent(mEvent);
        if (eventId ==0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/events/{eventId}").buildAndExpand(mEvent.getEventId()).toUri());
            return new ResponseEntity<>(eventId, HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<Void> editEvent(@RequestBody @Valid MEvent mEvent) {
        boolean flag = sEvent.updateEvent(mEvent);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") long eventId) {
        boolean flag = sEvent.deleteEvent(eventId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }


}
