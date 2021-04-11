package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.model.MEvent;
import esei.tfg.apachas.service.SEvent;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class CEvent {

    @Autowired
    @Qualifier("SEvent")
    SEvent sEvent;

    @PostMapping("/eventAdd")
    public ResponseEntity<Void> addEvent(@RequestBody @Valid MEvent mEvent, UriComponentsBuilder builder) {
        boolean flag = sEvent.insert(mEvent);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/eventSelect/{eventId}").buildAndExpand(mEvent.getEventId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/eventUpdate")
    public ResponseEntity<MEvent> updateEvent(@RequestBody @Valid MEvent mEvent) {
        boolean flag = sEvent.update(mEvent);
        if (!flag) {
            return new ResponseEntity<>(mEvent, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mEvent, HttpStatus.OK);
        }
    }

    @DeleteMapping("/eventDelete/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") long eventId) {
        boolean flag = sEvent.delete(eventId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/eventSelect/{eventId}")
    public ResponseEntity<MEvent> getEventById(@PathVariable("eventId") long eventId) {
        MEvent mEvent = sEvent.selectEventById(eventId);
        return new ResponseEntity<>(mEvent, HttpStatus.OK);
    }

    @GetMapping("/eventSelect")
    public ResponseEntity<List<MEvent>> getAllEvent() {
        List<MEvent> eventList = sEvent.selectAll();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("/eventPageableSelect")
    public ResponseEntity<List<MEvent>> getPageableEvent(Pageable pageable) {
        List<MEvent> eventList = sEvent.selectPageable(pageable);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
}
