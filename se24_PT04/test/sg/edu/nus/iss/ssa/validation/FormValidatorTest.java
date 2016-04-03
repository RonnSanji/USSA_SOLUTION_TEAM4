package sg.edu.nus.iss.ssa.validation;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.StoreKeeper;
import sg.edu.nus.iss.ssa.util.IOService;

public class FormValidatorTest extends TestCase {
	@Test
	public void testStoreKeeperValidateForm() {
		IOService<?> ioManager = new IOService<>();
		try {
			ioManager.readFromFile(FileDataWrapper.storeKeeperMap, null, new StoreKeeper());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		String msg;
		try {
			String emptyName = null;
			char[] emptyPassword = {};
			msg = FormValidator.addStoreKeeperValidateForm(emptyName, emptyPassword);
			assertTrue(msg.contains(StoreConstants.STOREKEEPER_NOT_FOUND));

			String upperCaseName = "Stacy";
			msg = FormValidator.addStoreKeeperValidateForm(upperCaseName, emptyPassword);
			assertTrue(msg.contains(StoreConstants.STOREKEEPER_INCORRECT_PASSWORD));

			char[] lowerCasePassword = { 'd', 'e', 'a', 'n', '5', '6', 's' };
			msg = FormValidator.addStoreKeeperValidateForm(upperCaseName, lowerCasePassword);
			assertTrue(msg.contains(StoreConstants.STOREKEEPER_INCORRECT_PASSWORD));

			char[] correctPassword = { 'D', 'e', 'a', 'n', '5', '6', 's' };
			msg = FormValidator.addStoreKeeperValidateForm(upperCaseName, correctPassword);
			assertTrue(msg == null);

			String lowerCassName = "stacy";
			msg = FormValidator.addStoreKeeperValidateForm(lowerCassName, correctPassword);
			assertTrue(msg == null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testAddCategoryValidateForm() {

		String msg;

		String testCategoryID = null;
		String testCategoryName = null;

		try {
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYID));

			testCategoryID = "";
			testCategoryName = "";
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYID));

			testCategoryID = "";
			testCategoryName = "test";
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYID));

			testCategoryID = "123";
			testCategoryName = "";
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYNAME));

			testCategoryID = "1";
			testCategoryName = "test";
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));

			testCategoryID = "12";
			testCategoryName = "test";
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));

			testCategoryID = "123";
			testCategoryName = "test";
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg == null);

			testCategoryID = "1234";
			testCategoryName = "test";
			msg = FormValidator.addEditCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testAddCategoryValidateData() {
		Random ran = new Random();

		String testExistingCategoryID = null;

		ArrayList<String> existingKeyList = new ArrayList<>();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				existingKeyList.add(key.toUpperCase());
			}
		}

		testExistingCategoryID = existingKeyList.get(ran.nextInt(existingKeyList.size()));
		Category cat = new Category();
		cat.setCategoryId(testExistingCategoryID);
		cat.setCategoryName(testExistingCategoryID);

		String msg;
		try {
			msg = FormValidator.addCategoryValidateData(cat);
			assertTrue(msg.contains(StoreConstants.CATEGORY_EXISTS));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
				+ letters.charAt(ran.nextInt(26));

		while (true) {
			if (existingKeyList.contains(newCategoryID)) {
				System.out.println("Category: " + newCategoryID + " exists");
				newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
						+ letters.charAt(ran.nextInt(26));
			} else {
				break;
			}

		}
		System.out.println("New category ID:" + newCategoryID);
		cat = new Category();
		cat.setCategoryId(newCategoryID);
		cat.setCategoryName(newCategoryID);
		try {
			msg = FormValidator.addCategoryValidateData(cat);
			assertTrue(msg == null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testRemoveCategoryValidateForm() {
		String categoryID = null;
		String msg;
		try {
			msg = FormValidator.removeCategoryValidateForm(categoryID);
			assertTrue(msg.contains(StoreConstants.SELECT_CATEGORY));

			categoryID = "";
			msg = FormValidator.removeCategoryValidateForm(categoryID);
			assertTrue(msg.contains(StoreConstants.SELECT_CATEGORY));

			categoryID = "test";
			msg = FormValidator.removeCategoryValidateForm(categoryID);
			assertTrue(msg == null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testEditRemoveCategoryValidateData() {
		Random ran = new Random();

		String testExistingCategoryID = null;

		ArrayList<String> existingKeyList = new ArrayList<>();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				existingKeyList.add(key.toUpperCase());
			}
		}

		testExistingCategoryID = existingKeyList.get(ran.nextInt(existingKeyList.size()));
		Category cat = new Category();
		cat.setCategoryId(testExistingCategoryID);
		cat.setCategoryName(testExistingCategoryID);
		String msg;
		try {
			msg = FormValidator.editRemoveCategoryValidateData(cat);
			assertTrue(msg == null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String newCategoryID = null;
		msg = FormValidator.editRemoveCategoryValidateData(null);
		assertTrue(msg.contains(StoreConstants.EMPTY_CATEGORY));

		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
				+ letters.charAt(ran.nextInt(26));

		cat = new Category();
		cat.setCategoryId(newCategoryID);
		cat.setCategoryName(newCategoryID);

		while (true) {
			if (existingKeyList.contains(newCategoryID)) {
				System.out.println("Category: " + newCategoryID + " exists");
				newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
						+ letters.charAt(ran.nextInt(26));
			} else {
				break;
			}
		}
		try {
			msg = FormValidator.editRemoveCategoryValidateData(cat);
			assertTrue(msg.contains(StoreConstants.CATEGORYID_NOT_EXIST));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testReplenishStockValidateForm() {
		String testQuantityString = null;
		String msg;
		try {
			msg = FormValidator.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.ENTER_REPLENISH_QUANTITY));

			testQuantityString = "";
			msg = FormValidator.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.ENTER_REPLENISH_QUANTITY));

			testQuantityString = "-1";
			msg = FormValidator.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.INVALID_REPLENISH_QUANTITY));

			testQuantityString = "0";
			msg = FormValidator.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.INVALID_REPLENISH_QUANTITY));

			testQuantityString = "test";
			msg = FormValidator.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.INVALID_REPLENISH_QUANTITY));

			testQuantityString = "123";
			msg = FormValidator.replenishStockValidateForm(testQuantityString);
			assertTrue(msg == null);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testConfigureThresholdReorderQuantityValidateForm() {
		String testthreshold = null;
		String testreorderQuantity = null;

		String msg = null;

		msg = FormValidator.configureThresholdReorderQuantityValidateForm(testthreshold, testreorderQuantity);
		assertTrue(msg.contains(StoreConstants.ENTER_NEW_THRESHOLD));

		testthreshold = "";
		msg = FormValidator.configureThresholdReorderQuantityValidateForm(testthreshold, testreorderQuantity);
		assertTrue(msg.contains(StoreConstants.ENTER_NEW_THRESHOLD));

		testthreshold = "test";
		msg = FormValidator.configureThresholdReorderQuantityValidateForm(testthreshold, testreorderQuantity);
		assertTrue(msg.contains(StoreConstants.INVALID_THRESHOLD_QUANTITY));

		testthreshold = "123";
		testreorderQuantity = "";
		msg = FormValidator.configureThresholdReorderQuantityValidateForm(testthreshold, testreorderQuantity);
		assertTrue(msg.contains(StoreConstants.ENTER_NEW_REORDER_QUANTITY));

		testreorderQuantity = "test";
		msg = FormValidator.configureThresholdReorderQuantityValidateForm(testthreshold, testreorderQuantity);
		assertTrue(msg.contains(StoreConstants.INVALID_REORDER_QUANTITY));

		testreorderQuantity = "456";
		msg = FormValidator.configureThresholdReorderQuantityValidateForm(testthreshold, testreorderQuantity);
		assertTrue(msg == null);

	}

	@Test
	public void testReplenishStockConfigureThresholdValidateData() {
		int barcode = 0;
		String msg = FormValidator.replenishStockConfigureThresholdValidateData(barcode);
		assertTrue(msg.contains(StoreConstants.INVALID_PRODUCT_BAR_CODE));

		barcode = -1;
		msg = FormValidator.replenishStockConfigureThresholdValidateData(barcode);
		assertTrue(msg.contains(StoreConstants.INVALID_PRODUCT_BAR_CODE));

		Random ran = new Random();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.productMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			System.out.println("products : " + FileDataWrapper.productMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		ArrayList<Integer> barcodes = new ArrayList<Integer>();

		for (Product p : FileDataWrapper.productMap.values()) {
			barcodes.add(p.getBarCode());
		}

		barcode = barcodes.get(ran.nextInt(barcodes.size()));

		msg = FormValidator.replenishStockConfigureThresholdValidateData(barcode);
		assertTrue(msg == null);
	}

	@Test
	public void testAddEditDiscountValidateForm() {
		String testdiscountCode = null;
		String testdiscountDescription = null;
		String teststartDateType = null;
		Date teststartDate = null;
		String testperiod = null;
		String testpercentage = null;
		String testapplicableTo = null;

		String msg = null;

		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_CODE));
			
		testdiscountCode = "";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_CODE));
		
		testdiscountCode = "test";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_DESCRIPTION));
		
		testdiscountDescription= "";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_DESCRIPTION));
		
		testdiscountDescription="test";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.SELECT_DISCOUNT_START_DATE));
		
		teststartDateType = "";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.SELECT_DISCOUNT_START_DATE));
		
		teststartDateType = "test";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.SELECT_DISCOUNT_START_DATE));
		
		teststartDateType = StoreConstants.PERIOD_DSCOUNT_START_DATE;
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.SELECT_DISCOUNT_START_DATE));
		
		teststartDate = new Date();
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_PERIOD));
		
		testperiod = "";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_PERIOD));
		
		teststartDateType = StoreConstants.PERMANENT_DSCOUNT_START_DATE;
		testperiod = "30";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERIOD));
		
		teststartDateType = StoreConstants.PERIOD_DSCOUNT_START_DATE;
		testperiod = "test";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERIOD));
		
		testperiod ="0";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERIOD));
		
		testperiod ="-10";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERIOD));
		
		testperiod ="35";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_PERCENTAGE));
		
		testpercentage = "";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_PERCENTAGE));
		
		testpercentage = "test";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERCENTAGE));
		
		testpercentage = "0";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERCENTAGE));
		
		testpercentage = "-3";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERCENTAGE));
		
		testpercentage = "100";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERCENTAGE));
		
		testpercentage = "110";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERCENTAGE));
		
		testpercentage = "20";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.SELECT_DISCOUNT_APPLICABLE_TO));
		
		testapplicableTo = "";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.SELECT_DISCOUNT_APPLICABLE_TO));
		
		testapplicableTo = "-select-";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.SELECT_DISCOUNT_APPLICABLE_TO));
		
		testapplicableTo = "test";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_APPLICABLE_TO));
		
		testapplicableTo = StoreConstants.MEMBER_DICSOUNT_NAME;
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg == null);
		
		testapplicableTo = StoreConstants.PUBLIC_DICSOUNT_NAME;
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg == null);
	}
	
	@Test
	public void testAddDiscountValidateData()
	{
		
	}
	
	@Test
	public void testEditRemoveDiscountValidateData()
	{
		
	}
	
	@Test
	public void testAddMemberValidateForm()
	{
		
	}
	
	@Test
	public void testAddStoreKeeperValidateForm()
	{
		
	}
	
	@Test
	public void testAddProductValidateForm()
	{
		
	}
	

}
