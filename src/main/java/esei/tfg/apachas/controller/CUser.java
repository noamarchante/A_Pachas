package esei.tfg.apachas.controller;

import esei.tfg.apachas.configuration.SecurityConfiguration;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.service.SUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUser {
    @Autowired
    @Qualifier("SUser")
    SUser sUser;

    @Autowired
    SecurityConfiguration securityConfiguration;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody @Valid MUser mUser, UriComponentsBuilder builder) {
        mUser.setUserPassword(securityConfiguration.passwordEncoder().encode(mUser.getUserPassword()));
        mUser.setRoles("USER");

        boolean flag = sUser.insert(mUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/{userId}").buildAndExpand(mUser.getUserId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<MUser> updateUser(@RequestBody @Valid MUser mUser) {
        mUser.setUserPassword(securityConfiguration.passwordEncoder().encode(mUser.getUserPassword()));

        boolean flag = sUser.update(mUser);
        if (!flag) {
            return new ResponseEntity<>(mUser, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUser, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
        boolean flag = sUser.delete(userId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MUser> getUserById(@PathVariable("userId") long userId) {
        MUser mUser = sUser.selectById(userId);
        return new ResponseEntity<>(mUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MUser>> getAllUser() {
        List<MUser> userList = sUser.selectAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<MUser>> getPageableUser(Pageable pageable) {
        List<MUser> userList = sUser.selectPageable(pageable);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
