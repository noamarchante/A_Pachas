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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usersUsers")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.DELETE})
public class CUserUser {
    @Autowired
    @Qualifier("SUserUser")
    SUserUser sUserUser;

    @PostMapping
    public ResponseEntity<Void> addUserUser(@RequestBody @Valid MUserUser mUserUser, UriComponentsBuilder builder) {
        boolean flag = sUserUser.insert(mUserUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/usersUsers/{friendId}/{userId}").buildAndExpand(mUserUser.getFriendId(), mUserUser.getUserId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<MUserUser> updateUserUser(@RequestBody @Valid MUserUser mUserUser) {
        boolean flag = sUserUser.update(mUserUser);
        if (!flag) {
            return new ResponseEntity<>(mUserUser, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserUser, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{friendId}/{userId}")
    public ResponseEntity<Void> deleteUserUser(@PathVariable("friendId") long friendId, @PathVariable("userId") long userId) {
        boolean flag = sUserUser.delete(new UserUserId(friendId,userId));
        if (!flag) {
            boolean flag2 = sUserUser.delete(new UserUserId(userId, friendId));
            if (!flag2) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*@GetMapping("/count/{authId}")
    public ResponseEntity<Long> countUsersUsersByAuthUser( @PathVariable("authId") Long authId) {
        long userCount = sUserUser.countByAuthUser(authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }*/

    @GetMapping("/{friendId}/{userId}")
    public ResponseEntity<MUserUser> getUserUserById(@PathVariable("friendId") long friendId, @PathVariable("userId") long userId) {
        try {
            MUserUser mUserUser = sUserUser.selectUserUserById(new UserUserId(friendId, userId));
           if (mUserUser == null){
                 mUserUser = sUserUser.selectUserUserById(new UserUserId(userId, friendId));
            }
            return new ResponseEntity<>(mUserUser, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

   /* @GetMapping("/{authId}")
    public ResponseEntity<List<MUserUser>> getAllUserUserByAuthUser(@PathVariable("authId") long authId) {
        List<MUserUser> userUserList = sUserUser.selectAllByAuthUser(authId);
        return new ResponseEntity<>(userUserList, HttpStatus.OK);
    }*/

    @GetMapping
    public ResponseEntity<List<MUserUser>> getAllUserUser() {
        List<MUserUser> userUserList = sUserUser.selectAll();
        return new ResponseEntity<>(userUserList, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<MUserUser>> getPageableUserUser(Pageable pageable) {
        List<MUserUser> userUserList = sUserUser.selectPageable(pageable);
        return new ResponseEntity<>(userUserList, HttpStatus.OK);
    }
}
