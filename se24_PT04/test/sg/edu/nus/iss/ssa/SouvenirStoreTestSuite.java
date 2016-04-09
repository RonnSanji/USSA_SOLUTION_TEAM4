package sg.edu.nus.iss.ssa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sg.edu.nus.iss.ssa.bo.DiscountOfferCalculator;
import sg.edu.nus.iss.ssa.bo.DiscountOfferCalculatorTest;
import sg.edu.nus.iss.ssa.bo.TotalReceiptCalculator;
import sg.edu.nus.iss.ssa.bo.TotalReceiptCalculatorTest;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.controller.EntityListControllerTest;
import sg.edu.nus.iss.ssa.exception.BarCodeException;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.PeriodDiscountTest;
import sg.edu.nus.iss.ssa.util.BarCodeGenerator;
import sg.edu.nus.iss.ssa.util.BarCodeGeneratorTest;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.DisplayUtilTest;
import sg.edu.nus.iss.ssa.validation.*;

/**
 * 
 * 
 * @author Amarjeet B Singh
 *
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DiscountOfferCalculatorTest.class,
        TotalReceiptCalculatorTest.class,
        EntityListControllerTest.class,
        PeriodDiscountTest.class,
        BarCodeGeneratorTest.class,
        DisplayUtilTest.class,
        FormValidatorTest.class,
        OrderValidatorTest.class,
        ReportVaildatorTest.class
})
public class SouvenirStoreTestSuite {
	

}
