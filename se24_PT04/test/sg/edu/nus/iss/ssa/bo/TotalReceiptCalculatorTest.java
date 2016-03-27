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
        order = new Order();
        transactions = new ArrayList<Transaction>();
        FileDataWrapper.transactionList.addAll(transactions);
        receiptCalculator = new TotalReceiptCalculator(order);
    }

    @Test
    public void testGetCashEquivalentPointstext() throws Exception {
        order.setPointsRedeemed(500l);
        String text = receiptCalculator.getCashEquivalentPointstext(order);
        assertEquals("500 (5.0$)",text);

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