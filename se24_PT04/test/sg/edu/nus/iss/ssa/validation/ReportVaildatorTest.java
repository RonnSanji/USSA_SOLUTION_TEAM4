/**
 * 
 */
package sg.edu.nus.iss.ssa.validation;

import static org.junit.Assert.*;


import org.junit.Test;

public class ReportVaildatorTest {

	@Test
	public void test() {

		String testDate = "2016-12-31";
		String testDate2 = "20160623";
		String testDate3 = "16-05-01";
		String testDate4 = "16-5-1";
		String testDate5 = "2016-12-32";
		ReportValidator rv = new ReportValidator();
		assertTrue(rv.isDateValid(testDate));
		assertFalse(rv.isDateValid(testDate2));
		assertFalse(rv.isDateValid(testDate3));
		assertFalse(rv.isDateValid(testDate4));
		assertFalse(rv.isDateValid(testDate5));
	}

}
