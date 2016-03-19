package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Product;

import java.util.Map;

/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class TotalReceiptCalculator {

    Order order = null;
    DiscountOfferCalculator offerCalculator = null;
    Map<Long, Product> productMap;

    public TotalReceiptCalculator(){

    }

    public TotalReceiptCalculator(Order order){
        this.order = order;
        offerCalculator = new DiscountOfferCalculator();
        productMap = FileDataWrapper.productMap;
    }

    public void processPayment() {
        Member member = order.getMemberInfo();
        if(member !=null ){
            long pointsEarned = offerCalculator.calculatePointsEqCash(order);
            long initialPoints = member.getLoyaltyPoints();
            long remainingPoints = initialPoints + pointsEarned - order.getPointsRedeemed();
            order.setPointsEarned(pointsEarned);
            member.setLoyaltyPoints(remainingPoints);
            double totalCashIncludingPoints = offerCalculator.getTotalCashIncludingPoints(order.getAmountTendered(),order.getPointsRedeemed());
            double amountToReturn = totalCashIncludingPoints - order.getFinalPrice();
            order.setReturnAmount(amountToReturn);
        }

        //update productQuantity
        for(LineItem item : order.getItems()){
            Product product = item.getProduct();
            System.out.println(product.toString());
            long remainingQty = product.getQuantity() - item.getBuyQuantity();
            product.setQuantity(remainingQty);
            productMap.put(product.getBarCode(),product);
            System.out.println(product.toString());
        }

    }


    /**
     * Returns String representation for points and equivalent cash
     * @return
     */
    public  String getCashEquivalentPointstext(Order order ) {
        StringBuilder sb = new StringBuilder();
        long pointsRedeemed = order.getPointsRedeemed();
        sb.append(pointsRedeemed).append(" (" ).append(offerCalculator.getCashValueForPoints(pointsRedeemed)).append(" $)");
        return sb.toString();
    }




}
