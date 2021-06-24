package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.model.MItem;
import esei.tfg.apachas.service.SItem;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CItem {
    @Autowired
    @Qualifier("SItem")
    SItem sItem;

    @PostMapping("/itemAdd")
    public ResponseEntity<Void> addItem(@RequestBody @Valid MItem mItem, UriComponentsBuilder builder) {
        boolean flag = sItem.insert(mItem);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/itemSelect/{itemId}").buildAndExpand(mItem.getItemId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/itemUpdate")
    public ResponseEntity<MItem> updateItem(@RequestBody @Valid MItem mItem) {
        boolean flag = sItem.update(mItem);
        if (!flag) {
            return new ResponseEntity<>(mItem, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mItem, HttpStatus.OK);
        }
    }

    @DeleteMapping("/itemDelete/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemId") long itemId) {
        boolean flag = sItem.delete(itemId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/itemSelect/{itemId}")
    public ResponseEntity<MItem> getItemById(@PathVariable("itemId") long itemId) {
        MItem mItem = sItem.selectItemById(itemId);
        return new ResponseEntity<>(mItem, HttpStatus.OK);
    }

    @GetMapping("/itemSelect")
    public ResponseEntity<List<MItem>> getAllItem() {
        List<MItem> itemList = sItem.selectAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/itemPageableSelect")
    public ResponseEntity<List<MItem>> getPageableItem(Pageable pageable) {
        List<MItem> itemList = sItem.selectPageable(pageable);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
}
