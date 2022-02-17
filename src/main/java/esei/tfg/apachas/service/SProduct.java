package esei.tfg.apachas.service;

import esei.tfg.apachas.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConProduct;
import esei.tfg.apachas.model.MProduct;
import esei.tfg.apachas.repository.REvent;
import esei.tfg.apachas.repository.RProduct;
import java.sql.Timestamp;
import java.util.List;

@Service("SProduct")
public class SProduct {

    @Autowired
    @Qualifier("RProduct")
    private RProduct rProduct;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("ConProduct")
    private ConProduct conProduct;

    public synchronized Long insertProduct(MProduct mProduct) {
        Product product = conProduct.conMProduct(mProduct);
        Product existingProduct = rProduct.findByProductId(product.getProductId());
        if (existingProduct != null) {
            return 0L;
        } else {
            product.setProductActive(true);
            product.setProductCreation(new Timestamp(System.currentTimeMillis()));
            product.setProductRemoval(null);
            return rProduct.save(product).getProductId();
        }
    }

    public synchronized boolean updateProduct(MProduct mProduct) {
        Product product = conProduct.conMProduct(mProduct);
        Product existingProduct = rProduct.findByProductId(product.getProductId());
        if (existingProduct != null) {
            rProduct.save(product);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean deleteProduct(long productId) {
        Product existingProduct = rProduct.findByProductId(productId);
        if (existingProduct != null) {
            existingProduct.setProductActive(false);
            existingProduct.setProductRemoval(new Timestamp(System.currentTimeMillis()));
            rProduct.save(existingProduct);
            return true;
        } else {
            return false;
        }
    }

    public synchronized Long countProducts(long eventId){
        return rProduct.countByEvent_EventIdAndProductActiveTrue(eventId);
    }
    public synchronized Long countSearchProducts(long eventId, String productName){
        return rProduct.countByEvent_EventIdAndProductNameContainingAndProductActiveTrue(eventId, productName);
    }

    public synchronized List<MProduct> selectPageableProducts(long eventId, Pageable pageable) {
        List<Product> productList = rProduct.findByEvent_EventIdAndProductActiveTrueOrderByProductNameAsc(eventId, pageable).getContent();
        return conProduct.conProductList(productList);
    }

    public synchronized List<MProduct> selectProducts(long eventId) {
        List<Product> productList = rProduct.findByEvent_EventIdAndProductActiveTrueOrderByProductNameAsc(eventId);
        return conProduct.conProductList(productList);
    }

    public synchronized List<MProduct> selectPageableSearchProducts(long eventId, String productName, Pageable pageable) {
        return conProduct.conProductList(rProduct.findByEvent_EventIdAndProductNameContainingAndProductActiveTrueOrderByProductNameAsc(eventId, productName, pageable).getContent());
    }
}
