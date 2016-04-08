package sg.edu.nus.iss.ssa.util;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;

import static org.junit.Assert.*;

/**
 * Created by Amar on 4/8/2016.
 */
public class BarCodeGeneratorTest {

    BarCodeGenerator barCodeGenerator ;
    @Before
    public void setUp() throws Exception {
        barCodeGenerator = new BarCodeGenerator();
    }

    @Test
    public void testGenerateBarCode() throws Exception {
        int first = barCodeGenerator.generateBarCode();
        int second = barCodeGenerator.generateBarCode();
        assertNotEquals(first,second,0);
    }
}