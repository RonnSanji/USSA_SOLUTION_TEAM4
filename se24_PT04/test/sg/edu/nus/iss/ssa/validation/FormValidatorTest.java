package sg.edu.nus.iss.ssa.validation;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.StoreKeeper;
import sg.edu.nus.iss.ssa.util.IOService;

public class FormValidatorTest extends TestCase
{
	@Test
	public void testStoreKeeperValidateForm()
	{
		IOService<?> ioManager = new IOService<>();
		try
		{
			ioManager.readFromFile( FileDataWrapper.storeKeeperMap,null, new StoreKeeper());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (FieldMismatchExcepion fieldMismatchExcepion)
		{
			fieldMismatchExcepion.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ioManager = null;
		}

		String msg;
		try
		{
			String emptyName = null;
			char[] emptyPassword = {};
			msg = FormValidator.addStoreKeeperValidateForm(emptyName, emptyPassword);
			assertTrue(msg.contains(StoreConstants.STOREKEEPER_NOT_FOUND));

			String upperCaseName = "Stacy";
			msg = FormValidator.addStoreKeeperValidateForm(upperCaseName, emptyPassword);
			assertTrue(msg.contains(StoreConstants.STOREKEEPER_INCORRECT_PASSWORD));
			
			char[] lowerCasePassword = {'d','e','a','n','5','6','s'};
			msg = FormValidator.addStoreKeeperValidateForm(upperCaseName, lowerCasePassword);
			assertTrue(msg.contains(StoreConstants.STOREKEEPER_INCORRECT_PASSWORD));
			
			char[] correctPassword = {'D','e','a','n','5','6','s'};
			msg = FormValidator.addStoreKeeperValidateForm(upperCaseName, correctPassword);
			assertTrue(msg == null);
			
			String lowerCassName = "stacy";
			msg = FormValidator.addStoreKeeperValidateForm(lowerCassName, correctPassword);
			assertTrue(msg == null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testAddCategoryValidateForm()
	{

		String msg;

		String testCategoryID = null;
		String testCategoryName = null;

		try
		{
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYID));

			testCategoryID = "";
			testCategoryName = "";
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYID));

			testCategoryID = "";
			testCategoryName = "test";
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYID));

			testCategoryID = "123";
			testCategoryName = "";
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.ENTER_CATEGORYNAME));

			testCategoryID = "1";
			testCategoryName = "test";
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));

			testCategoryID = "12";
			testCategoryName = "test";
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));

			testCategoryID = "123";
			testCategoryName = "test";
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg == null);

			testCategoryID = "1234";
			testCategoryName = "test";
			msg = FormValidator.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Test
	public void testAddCategoryValidateData()
	{
		Random ran = new Random();

		String testExistingCategoryID = null;

		ArrayList<String> existingKeyList = new ArrayList<>();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null)
		{
			ioManager = new IOService<>();
		}
		try
		{
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (FieldMismatchExcepion fieldMismatchExcepion)
		{
			fieldMismatchExcepion.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ioManager = null;
		}

		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		if (keySet != null && keySet.size() > 0)
		{
			for (String key : keySet)
			{
				existingKeyList.add(key.toUpperCase());
			}
		}

		testExistingCategoryID = existingKeyList.get(ran.nextInt(existingKeyList.size()));

		String msg;
		try
		{
			msg = FormValidator.addCategoryValidateData(testExistingCategoryID);
			assertTrue(msg.contains(StoreConstants.CATEGORY_EXISTS));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
				+ letters.charAt(ran.nextInt(26));

		while (true)
		{
			if (existingKeyList.contains(newCategoryID))
			{
				System.out.println("Category: " + newCategoryID + " exists");
				newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
						+ letters.charAt(ran.nextInt(26));
			}
			else
			{
				break;
			}

		}
		System.out.println("New category ID:" + newCategoryID);
		try
		{
			msg = FormValidator.addCategoryValidateData(newCategoryID);
			assertTrue(msg == null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Test
	public void testRemoveCategoryValidateForm()
	{
		String categoryID = null;
		String msg;
		try
		{
			msg = FormValidator.removeCategoryValidateForm(categoryID);
			assertTrue(msg.contains(StoreConstants.SELECT_CATEGORY));

			categoryID = "";
			msg = FormValidator.removeCategoryValidateForm(categoryID);
			assertTrue(msg.contains(StoreConstants.SELECT_CATEGORY));

			categoryID = "test";
			msg = FormValidator.removeCategoryValidateForm(categoryID);
			assertTrue(msg == null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Test
	public void testRemoveCategoryValidateData()
	{
		Random ran = new Random();

		String testExistingCategoryID = null;

		ArrayList<String> existingKeyList = new ArrayList<>();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null)
		{
			ioManager = new IOService<>();
		}
		try
		{
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (FieldMismatchExcepion fieldMismatchExcepion)
		{
			fieldMismatchExcepion.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ioManager = null;
		}

		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		if (keySet != null && keySet.size() > 0)
		{
			for (String key : keySet)
			{
				existingKeyList.add(key.toUpperCase());
			}
		}

		testExistingCategoryID = existingKeyList.get(ran.nextInt(existingKeyList.size()));

		String msg;
		try
		{
			msg = FormValidator.removeCategoryValidateData(testExistingCategoryID);
			assertTrue(msg == null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		String newCategoryID = null;
		msg = FormValidator.removeCategoryValidateData(newCategoryID);
		assertTrue(msg.contains(StoreConstants.SELECT_CATEGORY));

		newCategoryID = "";
		msg = FormValidator.removeCategoryValidateData(newCategoryID);
		assertTrue(msg.contains(StoreConstants.SELECT_CATEGORY));

		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
				+ letters.charAt(ran.nextInt(26));

		while (true)
		{
			if (existingKeyList.contains(newCategoryID))
			{
				System.out.println("Category: " + newCategoryID + " exists");
				newCategoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
						+ letters.charAt(ran.nextInt(26));
			}
			else
			{
				break;
			}
		}
		try
		{
			msg = FormValidator.removeCategoryValidateData(newCategoryID);
			assertTrue(msg.contains(StoreConstants.CATEGORYID_NOT_EXIST));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Test
	public void testReplenishStockValidateForm()
	{
		String testQuantityString = null;
		String msg;
		try
		{
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
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Test
	public void testReplenishStockValidateData()
	{
		int barcode = 0;
		String msg = FormValidator.replenishStockConfigureThresholdValidateData(barcode);
		assertTrue(msg.contains(StoreConstants.INVALID_PRODUCT_BAR_CODE));

		barcode = -1;
		msg = FormValidator.replenishStockConfigureThresholdValidateData(barcode);
		assertTrue(msg.contains(StoreConstants.INVALID_PRODUCT_BAR_CODE));

		Random ran = new Random();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.productMap.clear();
		if (ioManager == null)
		{
			ioManager = new IOService<>();
		}
		try
		{
			ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			System.out.println("products : " + FileDataWrapper.productMap.keySet());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (FieldMismatchExcepion fieldMismatchExcepion)
		{
			fieldMismatchExcepion.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ioManager = null;
		}

		ArrayList<Integer> barcodes = new ArrayList<Integer>();

		for (Product p : FileDataWrapper.productMap.values())
		{
			barcodes.add(p.getBarCode());
		}

		barcode = barcodes.get(ran.nextInt(barcodes.size()));

		msg = FormValidator.replenishStockConfigureThresholdValidateData(barcode);
		assertTrue(msg == null);
	}
}
