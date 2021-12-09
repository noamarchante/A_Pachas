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
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.PUT})
public class CUser {
    @Autowired
    @Qualifier("SUser")
    SUser sUser;

    @Autowired
    SecurityConfiguration securityConfiguration;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid MUser mUser, UriComponentsBuilder builder) {
        mUser.setUserPassword(securityConfiguration.passwordEncoder().encode(mUser.getUserPassword()));
        mUser.setRoles("USER");

        boolean flag = sUser.insertUser(mUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/{userId}").buildAndExpand(mUser.getUserId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @GetMapping("/count/{authId}")
    public ResponseEntity<Long> countUsers( @PathVariable("authId") long authId) {
        long userCount = sUser.countUsers(authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    @GetMapping("/count/{userLogin}/{authId}")
    public ResponseEntity<Long> countSearchUsers(@PathVariable("userLogin") String userLogin, @PathVariable("authId") long authId) {
        long userCount = sUser.countSearchUsers(userLogin, authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    @GetMapping("/pageable/{authId}")
    public ResponseEntity<List<MUser>> getPageableUsers(@PathVariable("authId") long authId, Pageable pageable) {

        List<MUser> userList = sUser.selectPageableUsers(authId, pageable);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{userLogin}/{authId}")
    public ResponseEntity<List<MUser>> getPageableSearchUsers(@PathVariable("userLogin") String userLogin, @PathVariable("authId") long authId, Pageable pageable) {
        List<MUser> userList = sUser.selectPageableSearchUsers(userLogin,authId,pageable);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{userLogin}")
    public ResponseEntity<MUser> getUser(@PathVariable("userLogin") String userLogin) {
        MUser mUser = sUser.selectUser(userLogin);
        return new ResponseEntity<>(mUser, HttpStatus.OK);
    }
}
