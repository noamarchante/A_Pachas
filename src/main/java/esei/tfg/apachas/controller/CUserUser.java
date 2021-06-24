package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.entity.id.UserUserId;
import esei.tfg.apachas.model.MUserUser;
import esei.tfg.apachas.service.SUserUser;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
public class CUserUser {
    @Autowired
    @Qualifier("SUserUser")
    SUserUser sUserUser;

    @PostMapping("/userUserAdd")
    public ResponseEntity<Void> addUserUser(@RequestBody @Valid MUserUser mUserUser, UriComponentsBuilder builder) {
        boolean flag = sUserUser.insert(mUserUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userUserSelect/{userUserId}").buildAndExpand(new UserUserId(mUserUser.getFriendId(), mUserUser.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    /*@PutMapping("/userUserUpdate")
    public ResponseEntity<MUserUser> updateUserUser(@RequestBody @Valid MUserUser mUserUser) {
        boolean flag = sUserUser.update(mUserUser);
        if (!flag) {
            return new ResponseEntity<>(mUserUser, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserUser, HttpStatus.OK);
        }
    }*/

    @DeleteMapping("/userUserDelete/{friendId}/{userId}")
    public ResponseEntity<Void> deleteUserUser(@PathVariable("friendId") long friendId, @PathVariable("userId") long userId) {
        boolean flag = sUserUser.delete(new UserUserId(friendId,userId));
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userUserSelect/{friendId}/{userId}")
    public ResponseEntity<MUserUser> getUserUserById(@PathVariable("friendId") long friendId, @PathVariable("userId") long userId) {
        MUserUser mUserUser = sUserUser.selectUserUserById(new UserUserId(friendId,userId));
        return new ResponseEntity<>(mUserUser, HttpStatus.OK);
    }

    @GetMapping("/userUserSelect")
    public ResponseEntity<List<MUserUser>> getAllUserUser() {
        List<MUserUser> userUserList = sUserUser.selectAll();
        return new ResponseEntity<>(userUserList, HttpStatus.OK);
    }

    @GetMapping("/userUserPageableSelect")
    public ResponseEntity<List<MUserUser>> getPageableUserUser(Pageable pageable) {
        List<MUserUser> userUserList = sUserUser.selectPageable(pageable);
        return new ResponseEntity<>(userUserList, HttpStatus.OK);
    }
}
