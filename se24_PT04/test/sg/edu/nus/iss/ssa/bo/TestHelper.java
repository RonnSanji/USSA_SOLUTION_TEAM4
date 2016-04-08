package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.model.*;

/**
 * Created by Amarjeet B Singh on 4/8/2016.
 */
public class TestHelper {

    public static PeriodDiscount createDiscount(String code, String startDate, String period, float discountPerc, String applicableTo){
        PeriodDiscount discount = new PeriodDiscount();
        discount.setDiscountCode(code);
        discount.setStarDate(startDate);
        discount.setDiscountPeriod(period);
        discount.setDiscountPerc(discountPerc);
        discount.setApplicableTo(applicableTo);
        return discount;
    }

    public static Transaction createTransaction(String productId, String memberId, long quantity ){
        Transaction txn = new Transaction();
        txn.setProductId(productId);
        txn.setMemberId(memberId);
        txn.setQuantity(quantity);
        return  txn;
    }

    public static Order createOrder(){
        Order order = new Order();
        LineItem item = createLineItem(10);
        order.addLineItem(item);
        return order;
    }

    public static Order createOrder(long pointsRedeemed){
        Order order = new Order();
        order.setPointsRedeemed(pointsRedeemed);
        LineItem item = createLineItem(10);
        order.addLineItem(item);
        return order;
    }

    public static Product createProduct(){
        Product product =  new Product("CLO/1","test product", "test product desc ", 100, 10.0, 111, 10,10);
        return product;
    }
    public static LineItem createLineItem(long buyQty){
        Product product = createProduct();
        LineItem item = new LineItem(product,10);
        return item;
    }

    public static Member createMember(){
        Member member = new Member("Amarjeet", "S123",100);
        return member;
    }

    public static Member createMember(long loyaltyPoints){
        Member member = new Member("Amarjeet", "S123",loyaltyPoints);
        return member;
    }
}
