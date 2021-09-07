package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.entity.id.UserGroupUserId;
import esei.tfg.apachas.model.MUserGroupUser;
import esei.tfg.apachas.service.SUserGroupUser;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usersGroupsUsers")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserGroupUser {
    @Autowired
    @Qualifier("SUserGroupUser")
    SUserGroupUser sUserGroupUser;

    @PostMapping
    public ResponseEntity<Void> addUserGroupUser(@RequestBody @Valid MUserGroupUser mUserGroupUser, UriComponentsBuilder builder) {
        boolean flag = sUserGroupUser.insert(mUserGroupUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userGroupUserSelect/{userGroupUserId}").buildAndExpand(new UserGroupUserId(mUserGroupUser.getUserGroupId(), mUserGroupUser.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    /*@PutMapping("/userGroupUserUpdate")
    public ResponseEntity<MUserGroupUser> updateUserGroupUser(@RequestBody @Valid MUserGroupUser mUserGroupUser) {
        boolean flag = sUserGroupUser.update(mUserGroupUser);
        if (!flag) {
            return new ResponseEntity<>(mUserGroupUser, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserGroupUser, HttpStatus.OK);
        }
    }*/

    @DeleteMapping("/{userGroupId}/{userId}")
    public ResponseEntity<Void> deleteUserGroupUser(@PathVariable("userGroupId") long userGroupId, @PathVariable("userId") long userId) {
        boolean flag = sUserGroupUser.delete(new UserGroupUserId(userGroupId,userId));
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{userGroupId}/{userId}")
    public ResponseEntity<MUserGroupUser> getUserGroupUserById(@PathVariable("userGroupId") long userGroupId, @PathVariable("userId") long userId) {
        MUserGroupUser mUserGroupUser = sUserGroupUser.selectUserGroupUserById(new UserGroupUserId(userGroupId,userId));
        return new ResponseEntity<>(mUserGroupUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MUserGroupUser>> getAllUserGroupUser() {
        List<MUserGroupUser> userGroupUserList = sUserGroupUser.selectAll();
        return new ResponseEntity<>(userGroupUserList, HttpStatus.OK);
    }

    @GetMapping("/userGroupUserPageableSelect")
    public ResponseEntity<List<MUserGroupUser>> getPageableUserGroupUser(Pageable pageable) {
        List<MUserGroupUser> userGroupUserList = sUserGroupUser.selectPageable(pageable);
        return new ResponseEntity<>(userGroupUserList, HttpStatus.OK);
    }
}
