package sg.edu.nus.iss.ssa;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import sg.edu.nus.iss.ssa.bo.DiscountOfferCalculator;
import sg.edu.nus.iss.ssa.bo.TotalReceiptCalculator;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.util.BarCodeGenerator;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidatorTest;
import sg.edu.nus.iss.ssa.validation.OrderValidator;
import sg.edu.nus.iss.ssa.validation.ReportVaildatorTest;

/**
 * 
 * 
 * @author Amarjeet B Singh
 *
 */

public class SouvenirStoreTestRunner {

        public static void main(String[] args) {
                Result result = JUnitCore.runClasses(SouvenirStoreTestSuite.class);
                for (Failure failure : result.getFailures()) {
                        System.out.println(failure.toString());
                }
                System.out.println(result.wasSuccessful());
        }

}
