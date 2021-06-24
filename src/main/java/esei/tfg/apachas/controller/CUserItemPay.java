package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.entity.id.UserItemId;
import esei.tfg.apachas.model.MUserItemPay;
import esei.tfg.apachas.service.SUserItemPay;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserItemPay {
    @Autowired
    @Qualifier("SUserItemPay")
    SUserItemPay sUserItemPay;

    @PostMapping("/userItemPayAdd")
    public ResponseEntity<Void> addUserItemPay(@RequestBody @Valid MUserItemPay mUserItemPay, UriComponentsBuilder builder) {
        boolean flag = sUserItemPay.insert(mUserItemPay);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userItemPaySelect/{userItemId}").buildAndExpand(new UserItemId(mUserItemPay.getItemId(), mUserItemPay.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/userItemPayUpdate")
    public ResponseEntity<MUserItemPay> updateUserItemPay(@RequestBody @Valid MUserItemPay mUserItemPay) {
        boolean flag = sUserItemPay.update(mUserItemPay);
        if (!flag) {
            return new ResponseEntity<>(mUserItemPay, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserItemPay, HttpStatus.OK);
        }
    }

    @DeleteMapping("/userItemPayDelete/{itemId}/{userId}")
    public ResponseEntity<Void> deleteUserItemPay(@PathVariable("itemId") long itemId, @PathVariable("userId") long userId) {
        boolean flag = sUserItemPay.delete(new UserItemId(itemId,userId));
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userItemPaySelect/{itemId}/{userId}")
    public ResponseEntity<MUserItemPay> getUserItemPayById(@PathVariable("itemId") long itemId ,@PathVariable("userId") long userId) {
        MUserItemPay mUserItemPay = sUserItemPay.selectUserItemPayById(new UserItemId(itemId,userId));
        return new ResponseEntity<>(mUserItemPay, HttpStatus.OK);
    }

    @GetMapping("/userItemPaySelect")
    public ResponseEntity<List<MUserItemPay>> getAllUserItemPay() {
        List<MUserItemPay> userItemPayList = sUserItemPay.selectAll();
        return new ResponseEntity<>(userItemPayList, HttpStatus.OK);
    }

    @GetMapping("/userItemPayPageableSelect")
    public ResponseEntity<List<MUserItemPay>> getPageableUserItemPay(Pageable pageable) {
        List<MUserItemPay> userItemPayList = sUserItemPay.selectPageable(pageable);
        return new ResponseEntity<>(userItemPayList, HttpStatus.OK);
    }
}
