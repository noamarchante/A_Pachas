package esei.tfg.apachas.controller;

import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.service.SUserGroup;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/usersGroupsUsers")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserGroupUser {
    @Autowired
    @Qualifier("SUserGroupUser")
    SUserGroupUser sUserGroupUser;
    @Autowired
    @Qualifier("SUserGroup")
    SUserGroup sUserGroup;

    @PostMapping
    public ResponseEntity<Void> addUserGroupUser(@RequestBody @Valid MUserGroupUser mUserGroupUser, UriComponentsBuilder builder) {
        boolean flag = sUserGroupUser.insertUserGroupUser(mUserGroupUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/usersGroupsUsers/{userGroupUserId}").buildAndExpand(new UserGroupUserId(mUserGroupUser.getUserGroupId(), mUserGroupUser.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @GetMapping("/count/{userGroupId}")
    public ResponseEntity<Long> countUsersGroupsUsers( @PathVariable("userGroupId") long userGroupId) {
        long userGroupUserCount = sUserGroupUser.countUsersGroupsUsersByUserGroupId(userGroupId);
        return new ResponseEntity<>(userGroupUserCount, HttpStatus.OK);
    }

    @GetMapping("/pageable/{userGroupId}")
    public ResponseEntity<List<MUser>> getPageableUsersGroupsUsersByUserGroupId(@PathVariable("userGroupId") long userGroupId, Pageable pageable) {
        List<MUser> mUserList = sUserGroupUser.selectPageableUserByUserGroupId(userGroupId, pageable);
        return new ResponseEntity<>(mUserList, HttpStatus.OK);
    }

    @GetMapping("/{userGroupId}")
    public ResponseEntity<List<MUser>> getUsersGroupsUsersByUserGroupId(@PathVariable("userGroupId") long userGroupId) {
        List<MUser> mUserList = sUserGroupUser.selectUserByUserGroupId(userGroupId);
        return new ResponseEntity<>(mUserList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> deleteUserGroupUser(@RequestBody @Valid MUserGroupUser mUserGroupUser) {
        boolean flag = sUserGroupUser.delete(mUserGroupUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{userGroupId}")
    public ResponseEntity<Void> updateUserGroupUser(@PathVariable("userGroupId") long userGroupId, @RequestBody @Valid List<Long> userIdList) {
        boolean flag = sUserGroupUser.update(userGroupId, userIdList);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }
/*
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
    }*/

}
