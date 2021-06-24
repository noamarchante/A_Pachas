package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.entity.id.UserEventId;
import esei.tfg.apachas.model.MUserEventParticipate;
import esei.tfg.apachas.service.SUserEventParticipate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserEventParticipate {

    @Autowired
    @Qualifier("SUserEventParticipate")
    SUserEventParticipate sUserEventParticipate;

    @PostMapping("/userEventParticipateAdd")
    public ResponseEntity<Void> addUserEventParticipate(@RequestBody @Valid MUserEventParticipate mUserEventParticipate, UriComponentsBuilder builder) {
        boolean flag = sUserEventParticipate.insert(mUserEventParticipate);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userEventSelect/{userEventId}").buildAndExpand(new UserEventId( mUserEventParticipate.getEventId(), mUserEventParticipate.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/userEventParticipateUpdate")
    public ResponseEntity<MUserEventParticipate> updateUserEventParticipate(@RequestBody @Valid MUserEventParticipate mUserEventParticipate) {
        boolean flag = sUserEventParticipate.update(mUserEventParticipate);
        if (!flag) {
            return new ResponseEntity<>(mUserEventParticipate, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserEventParticipate, HttpStatus.OK);
        }
    }

    @DeleteMapping("/userEventParticipateDelete/{eventId}/{userId}")
    public ResponseEntity<Void> deleteUserEventParticipate(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        boolean flag = sUserEventParticipate.delete(new UserEventId(eventId,userId));
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userEventParticipateSelect/{eventId}/{userId}")
    public ResponseEntity<MUserEventParticipate> getUserEventParticipateById(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        MUserEventParticipate mUserEventParticipate = sUserEventParticipate.selectUserEventById(new UserEventId(eventId,userId));
        return new ResponseEntity<>(mUserEventParticipate, HttpStatus.OK);
    }

    @GetMapping("/userEventParticipateSelect")
    public ResponseEntity<List<MUserEventParticipate>> getAllUserEventParticipate() {
        List<MUserEventParticipate> mUserEventParticipateList = sUserEventParticipate.selectAll();
        return new ResponseEntity<>(mUserEventParticipateList, HttpStatus.OK);
    }

    @GetMapping("/userEventParticipatePageableSelect")
    public ResponseEntity<List<MUserEventParticipate>> getPageableUserEventParticipate(Pageable pageable) {
        List<MUserEventParticipate> mUserEventParticipateList = sUserEventParticipate.selectPageable(pageable);
        return new ResponseEntity<>(mUserEventParticipateList, HttpStatus.OK);
    }
}
