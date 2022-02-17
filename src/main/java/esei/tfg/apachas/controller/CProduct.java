package esei.tfg.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import esei.tfg.apachas.model.MProduct;
import esei.tfg.apachas.service.SProduct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CProduct {
    @Autowired
    @Qualifier("SProduct")
    SProduct sProduct;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid MProduct mProduct, UriComponentsBuilder builder) {
        Long productId = sProduct.insertProduct(mProduct);
        System.err.println(productId);
        if (productId == 0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/products/{productId}").buildAndExpand(mProduct.getEventId()).toUri());
            return new ResponseEntity<>(productId, HttpStatus.CREATED);
        }
    }


    @PutMapping
    public ResponseEntity<Void> editProduct(@RequestBody @Valid MProduct mProduct) {
        boolean flag = sProduct.updateProduct(mProduct);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") long productId) {
        boolean flag = sProduct.deleteProduct(productId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/pageable/{productName}/{eventId}")
    public ResponseEntity<List<MProduct>> getPageableSearchProducts(@PathVariable("eventId") long eventId, @PathVariable("productName") String productName, Pageable pageable) {
        List<MProduct> productList = sProduct.selectPageableSearchProducts(eventId, productName,pageable);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{eventId}")
    public ResponseEntity<List<MProduct>> getPageableProducts(@PathVariable("eventId") long eventId, Pageable pageable) {
        List<MProduct> mProductList = sProduct.selectPageableProducts(eventId, pageable);
        return new ResponseEntity<>(mProductList, HttpStatus.OK);
    }

    @GetMapping("/count/{eventId}")
    public ResponseEntity<Long> countProducts( @PathVariable("eventId") long eventId) {
        long productCount = sProduct.countProducts(eventId);
        return new ResponseEntity<>(productCount, HttpStatus.OK);
    }

    @GetMapping("/count/{productName}/{eventId}")
    public ResponseEntity<Long> countSearchProducts(@PathVariable("eventId") long eventId, @PathVariable("productName") String productName) {
        long productCount = sProduct.countSearchProducts(eventId, productName);
        return new ResponseEntity<>(productCount, HttpStatus.OK);
    }
}
