package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.model.*;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class TotalReceiptCalculator {

    Order order = null;
    DiscountOfferCalculator offerCalculator = null;
    Map<String, Product> productMap;
    List<Transaction> transactionList;
    IOService<?> ioManager = new IOService<Entity>();


    public TotalReceiptCalculator(){

    }

    public TotalReceiptCalculator(Order order ){
        this.order = order;
        this.transactionList = FileDataWrapper.transactionList;
        this.productMap = FileDataWrapper.productMap;
        this.offerCalculator = new DiscountOfferCalculator(transactionList, FileDataWrapper.discounts);
    }

    public void processPayment() {
        Member member = order.getMemberInfo();
        if(member !=null ){
            long pointsEarned = offerCalculator.calculatePointsEqCash(order);
            long initialPoints = member.getLoyaltyPoints();
            long remainingPoints = initialPoints + pointsEarned - order.getPointsRedeemed();
            order.setPointsEarned(pointsEarned);
            member.setLoyaltyPoints(remainingPoints);
        }

        double totalCashIncludingPoints = offerCalculator.getTotalCashIncludingPoints(order.getAmountTendered(),order.getPointsRedeemed());
        double amountToReturn = DisplayUtil.roundOffTwoDecimalPlaces(totalCashIncludingPoints - order.getFinalPrice());
        order.setReturnAmount(amountToReturn);

        long transactionId = getLatestTransactionId();

        //update productQuantity and transaction
        for(LineItem item : order.getItems()){
            Product product = productMap.get(String.valueOf(item.getProduct().getBarCode()));
            if(product != null ){
                long remainingQty = product.getQuantity() - item.getBuyQuantity();
                product.setQuantity(remainingQty);
                Transaction transaction = new Transaction(transactionId,product.getProductId(),order.getMemberIdOfUser(),item.getBuyQuantity() );
                transactionList.add(transaction);
            }

            //update Transaction
            //System.out.println(transactionList);
            try {
                ioManager.writeToFile(productMap.values(),new Product());
                ioManager.writeToFile(transactionList,new Transaction());
                ioManager.writeToFile(FileDataWrapper.memberMap.values(),new Member());
                order = new Order();
            } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Returns String representation for points and equivalent cash
     * @return
     */
    public  String getCashEquivalentPointstext(Order order ) {
        StringBuilder sb = new StringBuilder();
        long pointsRedeemed = order.getPointsRedeemed();
        sb.append(pointsRedeemed).append(" (" ).append(offerCalculator.getCashValueForPoints(pointsRedeemed)).append("$)");
        return sb.toString();
    }

    private long getLatestTransactionId() {
        if(transactionList.isEmpty()){
            return 1;
        }
        return (transactionList.get(transactionList.size()-1).getTransactionId()+1);
    }


}
