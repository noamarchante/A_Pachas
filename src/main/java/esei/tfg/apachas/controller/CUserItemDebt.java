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
import esei.tfg.apachas.model.MUserItemDebt;
import esei.tfg.apachas.service.SUserItemDebt;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserItemDebt {
    @Autowired
    @Qualifier("SUserItemDebt")
    SUserItemDebt sUserItemDebt;

    @PostMapping("/userItemDebtAdd")
    public ResponseEntity<Void> addUserItemDebt(@RequestBody @Valid MUserItemDebt mUserItemDebt, UriComponentsBuilder builder) {
        boolean flag = sUserItemDebt.insert(mUserItemDebt);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userItemDebtSelect/{userItemId}").buildAndExpand(new UserItemId(mUserItemDebt.getItemId(), mUserItemDebt.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/userItemDebtUpdate")
    public ResponseEntity<MUserItemDebt> updateUserItemDebt(@RequestBody @Valid MUserItemDebt mUserItemDebt) {
        boolean flag = sUserItemDebt.update(mUserItemDebt);
        if (!flag) {
            return new ResponseEntity<>(mUserItemDebt, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserItemDebt, HttpStatus.OK);
        }
    }

    @DeleteMapping("/userItemDebtDelete/{itemId}/{userId}")
    public ResponseEntity<Void> deleteUserItemDebt(@PathVariable("itemId") long itemId, @PathVariable("userId") long userId) {
        boolean flag = sUserItemDebt.delete(new UserItemId(itemId,userId));
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userItemDebtSelect/{itemId}/{userId}")
    public ResponseEntity<MUserItemDebt> getUserItemDebtById(@PathVariable("itemId") long itemId ,@PathVariable("userId") long userId) {
        MUserItemDebt mUserItemDebt = sUserItemDebt.selectUserItemDebtById(new UserItemId(itemId,userId));
        return new ResponseEntity<>(mUserItemDebt, HttpStatus.OK);
    }

    @GetMapping("/userItemDebtSelect")
    public ResponseEntity<List<MUserItemDebt>> getAllUserItemDebt() {
        List<MUserItemDebt> userItemDebtList = sUserItemDebt.selectAll();
        return new ResponseEntity<>(userItemDebtList, HttpStatus.OK);
    }

    @GetMapping("/userItemDebtPageableSelect")
    public ResponseEntity<List<MUserItemDebt>> getPageableUserItemDebt(Pageable pageable) {
        List<MUserItemDebt> userItemDebtList = sUserItemDebt.selectPageable(pageable);
        return new ResponseEntity<>(userItemDebtList, HttpStatus.OK);
    }
}
