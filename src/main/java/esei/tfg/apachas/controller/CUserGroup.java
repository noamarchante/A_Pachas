package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.model.MUserGroup;
import esei.tfg.apachas.service.SUserGroup;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usersGroups")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserGroup {
    @Autowired
    @Qualifier("SUserGroup")
    SUserGroup sUserGroup;

    @PostMapping
    public ResponseEntity<Long> addUserGroup(@RequestBody @Valid MUserGroup mUserGroup, UriComponentsBuilder builder) {
        Long userGroupId = sUserGroup.insertUserGroup(mUserGroup);
        if (userGroupId ==0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userGroups/{userGroupId}").buildAndExpand(mUserGroup.getUserGroupId()).toUri());
            return new ResponseEntity<>(userGroupId, HttpStatus.CREATED);
        }
    }

    @GetMapping("/pageable/{authId}/{userGroupName}")
    public ResponseEntity<List<MUserGroup>> getPageableUserGroupUserByUserGroupName(@PathVariable("authId") long authId, @PathVariable("userGroupName") String userGroupName, Pageable pageable) {
        List<MUserGroup> userGroupList = sUserGroup.selectPageableUserGroupByUserGroupName(authId, userGroupName,pageable);
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{userId}")
    public ResponseEntity<List<MUserGroup>> getUserGroupsByUserId(@PathVariable("userId") long userId,  Pageable pageable) {
        List<MUserGroup> mUserGroupList = sUserGroup.selectUserGroupByUserId(userId, pageable);
        return new ResponseEntity<>(mUserGroupList, HttpStatus.OK);
    }

    @GetMapping("/count/{authId}")
    public ResponseEntity<Long> countUsers( @PathVariable("authId") long authId) {
        long userCount = sUserGroup.countUsersGroups(authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    @GetMapping("/count/{authId}/{userGroupName}")
    public ResponseEntity<Long> countSearchUsers(@PathVariable("userGroupName") String userGroupName, @PathVariable("authId") long authId) {
        long userCount = sUserGroup.countSearchUsersGroups(authId, userGroupName);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

   @PutMapping
    public ResponseEntity<MUserGroup> deleteUserGroup(@RequestBody @Valid MUserGroup mUserGroup) {
        boolean flag = sUserGroup.delete(mUserGroup);
        if (!flag) {
            return new ResponseEntity<>(mUserGroup, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserGroup, HttpStatus.OK);
        }
    }

    /*
    @DeleteMapping("/{userGroupId}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable("userGroupId") long userGroupId) {
        boolean flag = sUserGroup.delete(userGroupId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{userGroupId}")
    public ResponseEntity<MUserGroup> getUserGroupById(@PathVariable("userGroupId") long userGroupId) {
        MUserGroup mUserGroup = sUserGroup.selectUserGroupById(userGroupId);
        return new ResponseEntity<>(mUserGroup, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MUserGroup>> getAllUserGroup() {
        List<MUserGroup> userGroupList = sUserGroup.selectAll();
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<MUserGroup>> getPageableUserGroup(Pageable pageable) {
        List<MUserGroup> userGroupList = sUserGroup.selectPageable(pageable);
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }*/
}
