package sg.edu.nus.iss.ssa.model;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.iss.ssa.constants.StoreConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Amarjeet B  Singh .
 */
public class PeriodDiscountTest {

    PeriodDiscount discount ;
    @Before
    public void setUp() throws Exception {
        discount = new PeriodDiscount();
    }

    @Test
    public void testCheckIfDiscountAvailable() throws Exception {
        discount.setStarDate("2016-08-09");
        discount.setDiscountPeriod("1");
        discount.setDiscountPerc(90);
        boolean isApplicable = discount.checkIfDiscountAvailable();
        assertFalse(isApplicable);

        discount.setStarDate("2015-08-09");
        discount.setDiscountPeriod("20");
        discount.setDiscountPerc(90);
        boolean isApplicable1 = discount.checkIfDiscountAvailable();
        assertFalse(isApplicable1);

        discount.setStarDate("2016-03-01");
        discount.setDiscountPeriod("100");
        discount.setDiscountPerc(90);
        boolean isApplicable2 = discount.checkIfDiscountAvailable();
        assertTrue(isApplicable2);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(StoreConstants.DATE_FORMAT);
        String currentDate = dateFormat.format(cal.getTime());
        discount.setStarDate(currentDate);
        discount.setDiscountPeriod("1");
        discount.setDiscountPerc(90);
        boolean isApplicable3 = discount.checkIfDiscountAvailable();
        assertTrue(isApplicable3);
    }
}