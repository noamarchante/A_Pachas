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
@RequestMapping
@CrossOrigin
public class CUserGroup {
    @Autowired
    @Qualifier("SUserGroup")
    SUserGroup UserGroup;

    @PostMapping("/userGroupAdd")
    public ResponseEntity<Void> addUserGroup(@RequestBody @Valid MUserGroup mUserGroup, UriComponentsBuilder builder) {
        boolean flag = UserGroup.insert(mUserGroup);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userGroupSelect/{userGroupId}").buildAndExpand(mUserGroup.getUserGroupId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/userGroupUpdate")
    public ResponseEntity<MUserGroup> updateUserGroup(@RequestBody @Valid MUserGroup mUserGroup) {
        boolean flag = UserGroup.update(mUserGroup);
        if (!flag) {
            return new ResponseEntity<>(mUserGroup, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserGroup, HttpStatus.OK);
        }
    }

    @DeleteMapping("/userGroupDelete/{userGroupId}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable("userGroupId") long userGroupId) {
        boolean flag = UserGroup.delete(userGroupId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userGroupSelect/{userGroupId}")
    public ResponseEntity<MUserGroup> getUserGroupById(@PathVariable("userGroupId") long userGroupId) {
        MUserGroup mUserGroup = UserGroup.selectUserGroupById(userGroupId);
        return new ResponseEntity<>(mUserGroup, HttpStatus.OK);
    }

    @GetMapping("/userGroupSelect")
    public ResponseEntity<List<MUserGroup>> getAllUserGroup() {
        List<MUserGroup> userGroupList = UserGroup.selectAll();
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }

    @GetMapping("/userGroupPageableSelect")
    public ResponseEntity<List<MUserGroup>> getPageableUserGroup(Pageable pageable) {
        List<MUserGroup> userGroupList = UserGroup.selectPageable(pageable);
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }
}
