package sg.edu.nus.iss.ssa.bo;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.iss.ssa.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Amarjeet B Singh.
 */
public class DiscountOfferCalculatorTest {

    DiscountOfferCalculator offerCalculator;
    Order order;
    List<PeriodDiscount> discounts;
    List<Transaction>  transactions;

    @Before
    public void setUp() throws Exception {
        discounts = new ArrayList<>();
        transactions = new ArrayList<Transaction>();
        offerCalculator = new DiscountOfferCalculator(transactions, discounts);
        order = new Order();

    }

    @After
    public void tearDown() throws Exception {
        offerCalculator = null;
    }

    @Test
    public void testGetTotalCashIncludingPoints() throws Exception {
        final double totalCash = 100;
        final long points = 500; // 100 points will evaluate to  1 $
        double total = offerCalculator.getTotalCashIncludingPoints(totalCash,points);
        assertEquals(105,total,0);

        final double totalCash1 = 31.54;
        final long points1 = 100; // 100 points will evaluate to  1 $
        double total1 = offerCalculator.getTotalCashIncludingPoints(totalCash1,points1);
        assertEquals(32.54, total1, 0);

        final double totalCash2 = 23.454;
        final long points2 = 0; // 100 points will evaluate to  1 $
        double total2 = offerCalculator.getTotalCashIncludingPoints(totalCash2,points2);
        assertEquals(23.454,total2,0);

        final double totalCash3 = 99.7;
        final long points3 = -1; // 100 points will evaluate to  1 $
        double total3 = offerCalculator.getTotalCashIncludingPoints(totalCash3,points3);
        assertEquals(99.7,total3,0);
    }

    @Test
    public void testGetCashValueForPoints() throws Exception {
        final long points = -1; // 100 points will evaluate to  1 $
        double cash = offerCalculator.getCashValueForPoints(points);
        assertEquals(0, cash, 0);

        final long points1 = 100; // 100 points will evaluate to  1 $
        double cash1 = offerCalculator.getCashValueForPoints(points1);
        assertEquals(1, cash1, 0);

        final long points2 = 240; // 100 points will evaluate to  1 $
        double cash2 = offerCalculator.getCashValueForPoints(points2);
        assertEquals(2, cash2, 0);
    }

    @Test
    public void testCalculatePointsEqCash() throws Exception {
        order.setFinalPrice(100d);
        long points = offerCalculator.calculatePointsEqCash(order);
        assertEquals(100, points);

        order.setFinalPrice(0d);
        long points1 = offerCalculator.calculatePointsEqCash(order);
        assertEquals(0, points1);
    }

    @Test
    public void testGetDiscountForFirstTransaction() throws Exception {
        discounts.add(TestHelper.createDiscount("MEMBER_FIRST", "ALWAYS", "ALWAYS", 10f, "M"));
        float discount = offerCalculator.getDiscountForFirstTransaction();
        assertEquals(10, discount, 0);

    }

    @Test
    public void testGetDiscountForSubSequentTransaction() throws Exception {
        discounts.add(TestHelper.createDiscount("MEMBER_FIRST", "ALWAYS", "ALWAYS", 10f, "M"));
        discounts.add(TestHelper.createDiscount("MEMBER_SUBSEQ", "ALWAYS", "ALWAYS", 20f, "M"));
        float discount = offerCalculator.getDiscountForFirstTransaction();
        assertEquals(10, discount, 0);


    }

    @Test
    public void testIsFirstTransactionForMember() throws Exception {
        transactions.clear();
        boolean isFirstTxn = offerCalculator.isFirstTransactionForMember("S1234");
        assertTrue(isFirstTxn);
        transactions.add(TestHelper.createTransaction("STA/1", "S1234", 100));
        boolean isFirstTxn1 = offerCalculator.isFirstTransactionForMember("S1234");
        assertFalse(isFirstTxn1);
    }

    @Test
    public void testApplyDiscount() throws Exception {
        Order order  = TestHelper.createOrder();
        offerCalculator.applyDiscount(order);
        assertEquals(100.0, order.getTotalPrice(), 0);
        assertEquals(0.0, order.getApplicableDiscountPerc(), 0);
        assertEquals(0, order.getApplicableDiscountAmount(), 0);
        assertEquals(100.0, order.getFinalPrice(), 0);

        order.setUser(TestHelper.createMember());
        discounts.add(TestHelper.createDiscount("MEMBER_FIRST", "ALWAYS", "ALWAYS", 10f, "M"));
        offerCalculator.applyDiscount(order);
        assertEquals(100.0, order.getTotalPrice(), 0);
        assertEquals(10.0, order.getApplicableDiscountPerc(), 0);
        assertEquals(10, order.getApplicableDiscountAmount(), 0);
        assertEquals(90.0, order.getFinalPrice(), 0);
    }

}