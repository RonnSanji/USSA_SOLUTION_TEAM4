package sg.edu.nus.iss.ssa.validation;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Product;

import java.util.Map;

/**
 * Created by Amarjeet B Singh on 3/19/2016.
 */
public class OrderValidator {


    public String validateProductPurchaseInput(final String productBarCode, final String quantity){
        String errorMessage = null;
        if(productBarCode.equals("") || quantity.equals("")){
            return StoreConstants.BLANK_INPUT_FOR_PRODUCT_PURCHASE;
        }
        return errorMessage;
    }

    public String validateSelectedProduct(final String productBarCode, final  Map<String,Product> productMap){
        String errorMessage = null;
        if(productBarCode != null){
            Object selectedItem = productMap.get(productBarCode);
            if(selectedItem == null) {
                return StoreConstants.INVALID_PRODUCT;
            }
        }
        return errorMessage;
    }

    public String validateProductOrder(final Product product, long quantity) {
        if(product !=null ){
            return product.getQuantity() < quantity ? StoreConstants.INVALID_PRODUCT_QUANTITY: null;
        }
        return null;
    }
}
