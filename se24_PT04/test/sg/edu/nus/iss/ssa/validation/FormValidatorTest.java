package sg.edu.nus.iss.ssa.validation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.StoreKeeper;
import sg.edu.nus.iss.ssa.model.Vendor;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.util.TestUtil;

public class FormValidatorTest extends TestCase {
	EntityListController controller;
	IOService<?> ioManager;

	@Before
	public void setUp() throws Exception {
		controller = new EntityListController();
		ioManager = new IOService<>();
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
		ioManager = null;
	}

	@Test
	public void testaddEditCategoryValidateForm() {
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
	public void testaddCategoryValidateData() {
		Random ran = new Random();

		String testExistingCategoryID = null;

		ArrayList<String> existingKeyList = new ArrayList<>();

		FileDataWrapper.categoryMap.clear();

		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

		String newCategoryID = TestUtil.generateRandomString(3, existingKeyList, false);

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
	public void testremoveCategoryValidateForm() {
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
	public void testeditRemoveCategoryValidateData() {
		Random ran = new Random();

		String testExistingCategoryID = null;

		ArrayList<String> existingKeyList = new ArrayList<>();

		FileDataWrapper.categoryMap.clear();

		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

		newCategoryID = TestUtil.generateRandomString(3, existingKeyList, false);

		cat = new Category();
		cat.setCategoryId(newCategoryID);
		cat.setCategoryName(newCategoryID);

		try {
			msg = FormValidator.editRemoveCategoryValidateData(cat);
			assertTrue(msg.contains(StoreConstants.CATEGORYID_NOT_EXIST));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testreplenishStockValidateForm() {
		String testQuantityString = null;
		String msg;

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

	}

	@Test
	public void testwriteOffStockValidateForm() {
		long testcurentQuantity = 0;
		String teststockTxt = null;

		String msg;

		msg = FormValidator.writeOffStockValidateForm(testcurentQuantity, teststockTxt);
		assertTrue(msg.contains(StoreConstants.ENTER_WRITEOFF_QUANTITY));

		teststockTxt = "";
		msg = FormValidator.writeOffStockValidateForm(testcurentQuantity, teststockTxt);
		assertTrue(msg.contains(StoreConstants.ENTER_WRITEOFF_QUANTITY));

		teststockTxt = "test";
		msg = FormValidator.writeOffStockValidateForm(testcurentQuantity, teststockTxt);
		assertTrue(msg.contains(StoreConstants.INVALID_WRITEOFF_QUANTITY));

		teststockTxt = "-1";
		msg = FormValidator.writeOffStockValidateForm(testcurentQuantity, teststockTxt);
		assertTrue(msg.contains(StoreConstants.INVALID_WRITEOFF_QUANTITY));

		testcurentQuantity = 1;
		teststockTxt = "2";
		msg = FormValidator.writeOffStockValidateForm(testcurentQuantity, teststockTxt);
		assertTrue(msg.contains(StoreConstants.INVALID_WRITEOFF_QUANTITY));

		teststockTxt = "0";
		msg = FormValidator.writeOffStockValidateForm(testcurentQuantity, teststockTxt);
		assertTrue(msg.contains(StoreConstants.INVALID_WRITEOFF_QUANTITY));

		teststockTxt = "1";
		msg = FormValidator.writeOffStockValidateForm(testcurentQuantity, teststockTxt);
		assertTrue(msg == null);

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

		FileDataWrapper.productMap.clear();

		try {
			ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			System.out.println("products : " + FileDataWrapper.productMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

		testdiscountDescription = "";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.ENTER_DISCOUNT_DESCRIPTION));

		testdiscountDescription = "test";
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

		testperiod = "0";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERIOD));

		testperiod = "-10";
		msg = FormValidator.addEditDiscountValidateForm(testdiscountCode, testdiscountDescription, teststartDateType,
				teststartDate, testperiod, testpercentage, testapplicableTo);
		assertTrue(msg.contains(StoreConstants.INVALID_DISCOUNT_PERIOD));

		testperiod = "35";
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
	public void testAddDiscountValidateData() {
		PeriodDiscount selectedDiscount = null;

		String msg = FormValidator.addDiscountValidateData(selectedDiscount);
		assertTrue(msg.contains(StoreConstants.EMPTY_DISCOUNT));

		controller.reloadDiscountData();

		selectedDiscount = (PeriodDiscount) FileDataWrapper.discounts
				.get(new Random().nextInt(FileDataWrapper.discounts.size()));

		msg = FormValidator.addDiscountValidateData(selectedDiscount);
		assertTrue(
				msg.contains("Discount: " + selectedDiscount.getDiscountCode() + " " + StoreConstants.DISCOUNT_EXIST));

		ArrayList<String> discountCodeList = new ArrayList<>();

		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			discountCodeList.add(tempDiscount.getDiscountCode());
		}

		String ranStr = TestUtil.generateRandomString(20, discountCodeList, false);

		selectedDiscount = new PeriodDiscount();
		selectedDiscount.setDiscountCode(ranStr);

		msg = FormValidator.addDiscountValidateData(selectedDiscount);
		assertTrue(msg == null);

	}

	@Test
	public void testEditRemoveDiscountValidateData() {
		controller.reloadDiscountData();

		PeriodDiscount discount = null;

		String msg = FormValidator.editRemoveDiscountValidateData(discount);
		assertTrue(msg.contains(StoreConstants.EMPTY_DISCOUNT));

		Random ran = new Random();

		discount = (PeriodDiscount) FileDataWrapper.discounts.get(ran.nextInt(FileDataWrapper.discounts.size()));

		msg = FormValidator.editRemoveDiscountValidateData(discount);
		assertTrue(msg == null);

		ArrayList<String> discountCodeList = new ArrayList<>();

		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			discountCodeList.add(tempDiscount.getDiscountCode());
		}

		String ranStr = TestUtil.generateRandomString(20, discountCodeList, false);

		discount = new PeriodDiscount();
		discount.setDiscountCode(ranStr);

		msg = FormValidator.editRemoveDiscountValidateData(discount);
		assertTrue(msg.contains("Discount: " + ranStr + " " + StoreConstants.DISCOUNT_NOT_EXIST));

	}

	@Test
	public void testaddMemberValidateForm() {
		String testmemberName = null;
		String testmemberNumber = null;

		String msg;

		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg.contains(StoreConstants.BLANK_MEMBER_NAME));

		testmemberName = "";
		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg.contains(StoreConstants.BLANK_MEMBER_NAME));

		testmemberName = " ,";
		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg.contains(StoreConstants.INVALID_NEWMEMBER_NAME));

		testmemberName = "test \n";
		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg.contains(StoreConstants.INVALID_NEWMEMBER_NAME));

		testmemberName = "test";
		testmemberNumber = "";
		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg.contains(StoreConstants.BLANK_MEMBER_NUMBER));

		testmemberNumber = " ,";
		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg.contains(StoreConstants.INVALID_NEWMEMBER_NUMBER));

		testmemberNumber = "test \n";
		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg.contains(StoreConstants.INVALID_NEWMEMBER_NUMBER));

		testmemberNumber = "123456789";
		msg = FormValidator.addMemberValidateForm(testmemberName, testmemberNumber);
		assertTrue(msg == null);

	}

	@Test
	public void testeditMemeberValidateForm() {
		String testmemberName = null;
		String testmemberNumber = null;
		long testpoint = 0;

		String msg;

		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == StoreConstants.BLANK_MEMBER_NAME);

		testmemberName = "";
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == StoreConstants.BLANK_MEMBER_NAME);

		testmemberName = " ,";
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == StoreConstants.INVALID_NEWMEMBER_NAME);

		testmemberName = "test \n";
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == StoreConstants.INVALID_NEWMEMBER_NAME);

		testmemberName = "test";
		testmemberNumber = "";
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == StoreConstants.BLANK_MEMBER_NUMBER);

		testmemberNumber = " ,";
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg.contains(StoreConstants.INVALID_NEWMEMBER_NUMBER));

		testmemberNumber = "test \n";
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == StoreConstants.INVALID_NEWMEMBER_NUMBER);

		testmemberNumber = "123456789";
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == null);

		testpoint = -1;
		msg = FormValidator.editMemeberValidateForm(testmemberName, testmemberNumber, testpoint);
		assertTrue(msg == StoreConstants.INVALID_LOYLTY_POINT);

	}

	@Test
	public void testaddStoreKeeperValidateForm() {
		try {
			ioManager.readFromFile(FileDataWrapper.storeKeeperMap, null, new StoreKeeper());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

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
	}

	@Test
	public void testaddProductValidateForm() {
		String testcategoryName = null, testproductName = null, testproductDescription = null,
				testquantityAvailable = null, testprice = null, testthresholdQuantity = null,
				testreorderQuantity = null;

		String msg;

		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.BLANK_CATEGORYNAME);

		testcategoryName = "";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.BLANK_CATEGORYNAME);

		testcategoryName = " ,";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_CATERORY);

		testcategoryName = " \n";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_CATERORY);

		testcategoryName = "123";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_NAME);

		testproductName = "";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_NAME);

		testproductName = ", ";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_NAME);

		testproductName = " \n";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_NAME);

		testproductName = "test";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_DESCRIPTION);

		testproductDescription = "";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_DESCRIPTION);

		testproductDescription = " ,";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_DESCRIPTION);

		testproductDescription = " \n";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_DESCRIPTION);

		testproductDescription = "test";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_QUANTITY);

		testquantityAvailable = "";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_QUANTITY);

		testquantityAvailable = "a";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_QUANTITY);

		testquantityAvailable = "-1";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_QUANTITY);

		testquantityAvailable = "0";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_PRICE);

		testquantityAvailable = "100";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_PRICE);

		testprice = "";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_PRICE);

		testprice = "a";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_PRICE);

		testprice = "-1";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_PRICE);

		testprice = "0";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_PRICE);

		testprice = "1";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_THRESHOLD);

		testthresholdQuantity = "";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_THRESHOLD);

		testthresholdQuantity = ", ";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_THRESHOLD);

		testthresholdQuantity = "\n 123";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_THRESHOLD);

		testthresholdQuantity = "-1";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_THRESHOLD);

		testthresholdQuantity = "0";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_THRESHOLD);

		testthresholdQuantity = "10";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_REORDER_QUANTITY);

		testreorderQuantity = "";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.EMPTY_REORDER_QUANTITY);

		testreorderQuantity = ",";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_REORDER_QUANTITY);

		testreorderQuantity = " \n";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_REORDER_QUANTITY);

		testreorderQuantity = "-1";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_REORDER_QUANTITY);

		testreorderQuantity = "0";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == StoreConstants.INVALID_REORDER_QUANTITY);

		testreorderQuantity = "100";
		msg = FormValidator.addProductValidateForm(testcategoryName, testproductName, testproductDescription,
				testquantityAvailable, testprice, testthresholdQuantity, testreorderQuantity);
		assertTrue(msg == null);

	}

	@Test
	public void testaddEditVendorValidateForm() {
		String testvendorCode = null;
		String testvendorName = null;

		String msg;
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.ENTER_VENDOR_CODE);

		testvendorCode = "";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.ENTER_VENDOR_CODE);

		testvendorCode = " ,";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.INVALID_VENDOR_CODE);

		testvendorCode = " \n";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.INVALID_VENDOR_CODE);

		testvendorCode = "test";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.ENTER_VENDOR_NAME);

		testvendorName = "";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.ENTER_VENDOR_NAME);

		testvendorName = ",  ";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.INVALID_VENDOR_NAME);

		testvendorName = "   \n";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == StoreConstants.INVALID_VENDOR_NAME);

		testvendorName = "test";
		msg = FormValidator.addEditVendorValidateForm(testvendorCode, testvendorName);
		assertTrue(msg == null);
	}

	@Test
	public void testremoveVendorValidateForm() {
		String testvendorCode = null;

		String msg;
		msg = FormValidator.removeVendorValidateForm(testvendorCode);
		assertTrue(msg == StoreConstants.SELECT_VENDOR);

		testvendorCode = "test";
		msg = FormValidator.removeVendorValidateForm(testvendorCode);
		assertTrue(msg == null);
	}

	@Test
	public void testeditRemoveVendorValidateData() {

		controller.reloadCategoryData();
		controller.loadAllVendorMap();

		Vendor testselectedVendor = null;

		String msg;
		msg = FormValidator.editRemoveVendorValidateData(testselectedVendor);
		assertTrue(msg == StoreConstants.EMPTY_VENDOR);

		testselectedVendor = new Vendor();
		testselectedVendor.setVendorId(TestUtil.generateRandomString(50));
		msg = FormValidator.editRemoveVendorValidateData(testselectedVendor);
		assertTrue(msg.contains("Vendor: ") && msg.contains(StoreConstants.VENDOR_NOT_EXIST));

		Set<String> keys = FileDataWrapper.vendorMap.keySet();
		for (String key : keys) {
			List<Vendor> list = FileDataWrapper.vendorMap.get(key);
			if (list != null && list.size() > 0) {
				testselectedVendor = list.get(new Random().nextInt(list.size()));
				controller.reloadVendorDataByCategoryID(key);
				break;
			}
		}
		msg = FormValidator.editRemoveVendorValidateData(testselectedVendor);
		assertTrue(msg == null);

	}

	@Test
	public void testaddVendorValidateData() {

		controller.reloadCategoryData();
		controller.loadAllVendorMap();

		Vendor testselectedVendor = null;
		String msg;
		msg = FormValidator.addVendorValidateData(testselectedVendor);
		assertTrue(msg == StoreConstants.EMPTY_VENDOR);

		testselectedVendor = new Vendor();
		testselectedVendor.setVendorId(TestUtil.generateRandomString(50));
		msg = FormValidator.addVendorValidateData(testselectedVendor);
		assertTrue(msg == null);

		Set<String> keys = FileDataWrapper.vendorMap.keySet();
		for (String key : keys) {
			List<Vendor> list = FileDataWrapper.vendorMap.get(key);
			if (list != null && list.size() > 0) {
				testselectedVendor = list.get(new Random().nextInt(list.size()));
				controller.reloadVendorDataByCategoryID(key);
				break;
			}
		}
		msg = FormValidator.addVendorValidateData(testselectedVendor);
		assertTrue(msg.contains("Vendor: ") && msg.contains(StoreConstants.VENDOR_EXIST));
	}

	@Test
	public void testeditProductValidateForm() {
		String testproductName = null;
		String testproductDescription = null;
		double testprice = 0;

		String msg;

		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_NAME);


		testproductName = "";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_NAME);

		testproductName = ", ";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_NAME);

		testproductName = " \n";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_NAME);

		testproductName = "test";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_DESCRIPTION);

		testproductDescription = "";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.EMPTY_PRODUCT_DESCRIPTION);

		testproductDescription = " ,";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_DESCRIPTION);

		testproductDescription = " \n";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.INVALID_PRODUCT_DESCRIPTION);

		testproductDescription = "test";
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.INVALID_PRICE);

		testprice = -1;
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == StoreConstants.INVALID_PRICE);
		
		testprice = 100;
		msg = FormValidator.editProductValidateForm(testproductName, testproductDescription, testprice);
		assertTrue(msg == null);
	
	}
}
