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
		assertTrue(rv.isDateValid(testDate2));
		assertTrue(rv.isDateValid(testDate3));
		assertTrue(rv.isDateValid(testDate4));
		assertTrue(rv.isDateValid(testDate5));
	}

}
