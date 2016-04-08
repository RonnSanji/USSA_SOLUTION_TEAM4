package sg.edu.nus.iss.ssa.bo;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.iss.ssa.model.Discount;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Amarjeet B Singh
 */
public class TotalReceiptCalculatorTest {

    TotalReceiptCalculator receiptCalculator;
    DiscountOfferCalculator offerCalculator;
    Order order;
    List<Transaction>  transactions;
    FileDataWrapper fileDataWrapper;


    @Before
    public void setUp() throws Exception {
        transactions = new ArrayList<Transaction>();
        FileDataWrapper.transactionList.addAll(transactions);
        receiptCalculator = new TotalReceiptCalculator(order);
    }

    @Test
    public void testGetCashEquivalentPointstext() throws Exception {
        order = TestHelper.createOrder(500);
        String text = receiptCalculator.getCashEquivalentPointstext(order);
        assertEquals("500 (5.0$)",text);
    }

    @Test
    public void testProcessPayment()  {
        order = TestHelper.createOrder(100);
        order.setUser(TestHelper.createMember(500));
        receiptCalculator = new TotalReceiptCalculator(order);
        receiptCalculator.processPayment();

        assertEquals(400,order.getMemberInfo().getLoyaltyPoints(),0);

    }



    private PeriodDiscount createDiscount(String code, String startDate, String period, float discountPerc, String applicableTo){
        PeriodDiscount discount = new PeriodDiscount();
        discount.setDiscountCode(code);
        discount.setStarDate(startDate);
        discount.setDiscountPeriod(period);
        discount.setDiscountPerc(discountPerc);
        discount.setApplicableTo(applicableTo);
        return discount;
    }

}