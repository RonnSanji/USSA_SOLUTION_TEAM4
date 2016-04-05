package sg.edu.nus.iss.ssa.util;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.iss.ssa.model.Discount;

import static org.junit.Assert.*;

/**
 * Created by Amarjeet B Singh on 3/27/2016.
 */
public class DisplayUtilTest {

    DisplayUtil displayUtil;
    @Before
    public void setUp() throws Exception {
        displayUtil = new DisplayUtil();
    }

    @Test
    public void testRoundOffTwoDecimalPlaces() throws Exception {
        double number = 12.3455d;
        double rounded = displayUtil.roundOffTwoDecimalPlaces(number);
        assertEquals(12.35d,rounded,0);

        double number1 = 12.3d;
        double rounded1 = displayUtil.roundOffTwoDecimalPlaces(number1);
        assertEquals(12.30d,rounded1,0);

        double number2 = 12.3642d;
        double rounded2 = displayUtil.roundOffTwoDecimalPlaces(number2);
        assertEquals(12.36d,rounded2,0);

        double number3 = 12.0d;
        double rounded3 = displayUtil.roundOffTwoDecimalPlaces(number3);
        assertEquals(12.00d,rounded3,0);
    }
}