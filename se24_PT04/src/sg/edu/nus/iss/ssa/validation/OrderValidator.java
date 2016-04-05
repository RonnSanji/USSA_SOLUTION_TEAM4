package sg.edu.nus.iss.ssa.validation;

import sg.edu.nus.iss.ssa.bo.DiscountOfferCalculator;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Product;

import java.util.Map;

/**
 * Created by Amarjeet B Singh on 3/19/2016.
 */
public class OrderValidator {

    DiscountOfferCalculator offerCalculator ;

    public  OrderValidator(){

    }

    public OrderValidator(DiscountOfferCalculator offerCalculator){
        this.offerCalculator = offerCalculator;
    }



    public String validateProductPurchaseInput(final String productBarCode, final String quantity){
        String errorMessage = null;
        if(productBarCode.equals("") || quantity.equals("") ){
            return StoreConstants.BLANK_INPUT_FOR_PRODUCT_PURCHASE;
        }
        return errorMessage;
    }

    public String validateSelectedProduct(final String productBarCode, final  Map<?,Product> productMap){
        String errorMessage = null;
        if(productBarCode != null){
            Product selectedItem = productMap.get(productBarCode);
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

    /**
     * Points can be redeemed in multiple of 100.
     * @param redeemedPoints
     * @return
     */
    public String validateRedeemedPoints(long redeemedPoints, double renderedCash, Order receipt){
        long totalAvlPoints = receipt.getMemberInfo()!= null ? receipt.getMemberInfo().getLoyaltyPoints(): 0;
        if(totalAvlPoints <= 0 ){
            return null;
        }
        double finalCost = receipt.getFinalPrice();
        if(redeemedPoints > totalAvlPoints ){
            return StoreConstants.MAX_POINTS_REDEEMED_MSG + totalAvlPoints;
        }
        if(redeemedPoints % StoreConstants.CASH_EQ_POINTS !=0){
            return StoreConstants.POINTS_REDEEMED_CONSTRAINT;
        }

        return null;
    }

    public String checkAmountToProcessPayment(Long redeemedPoints, Double renderedCash, Order order) {
        if( offerCalculator.getTotalCashIncludingPoints(renderedCash,redeemedPoints) < order.getFinalPrice() ){
            return StoreConstants.NOT_ENOUGH_CASH;
        }
        return null;
    }

}
