package esei.tfg.apachas.converter;

import esei.tfg.apachas.entity.UserProduct;
import esei.tfg.apachas.entity.id.UserProductId;
import esei.tfg.apachas.model.MUserProduct;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserProduct")
public class ConUserProduct {

    public List<MUserProduct> conUserProductList(List<UserProduct> userProductList) {
        List<MUserProduct> mUserProductList = new ArrayList<>();
        for (UserProduct userProduct : userProductList) {
            mUserProductList.add(new MUserProduct(userProduct));
        }
        return mUserProductList;
    }

    public MUserProduct conUserProduct(UserProduct userProduct) {
        return new MUserProduct(userProduct);
    }

    public UserProduct conMUserProduct(MUserProduct mUserProduct) {
        return new UserProduct(new UserProductId(mUserProduct.getProductId(), mUserProduct.getUserId()));
    }
}
