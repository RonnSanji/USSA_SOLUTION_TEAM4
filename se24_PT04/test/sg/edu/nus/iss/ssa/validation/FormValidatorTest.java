package sg.edu.nus.iss.ssa.validation;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.util.IOService;

public class FormValidatorTest extends TestCase
{
	@Test
	public void testaddCategoryValidateForm()
	{
		FormValidator val = new FormValidator();
		String msg;

		String testCategoryID = null;
		String testCategoryName = null;

		try
		{
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.BLANK_CATEGORYID));

			testCategoryID = "";
			testCategoryName = "";
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.BLANK_CATEGORYID));

			testCategoryID = "";
			testCategoryName = "test";
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.BLANK_CATEGORYID));

			testCategoryID = "123";
			testCategoryName = "";
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.BLANK_CATEGORYNAME));

			testCategoryID = "1";
			testCategoryName = "test";
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));

			testCategoryID = "12";
			testCategoryName = "test";
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));

			testCategoryID = "123";
			testCategoryName = "test";
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg == null);

			testCategoryID = "1234";
			testCategoryName = "test";
			msg = val.addCategoryValidateForm(testCategoryID, testCategoryName);
			assertTrue(msg.contains(StoreConstants.CATEGORY_3_LETTERS));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			val = null;
		}
	}

	@Test
	public void testaddCategoryValidateData()
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

		FormValidator val = new FormValidator();
		String msg;
		try
		{
			msg = val.addCategoryValidateData(testExistingCategoryID);
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
			msg = val.addCategoryValidateData(newCategoryID);
			assertTrue(msg == null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			val = null;
		}
	}

	@Test
	public void testreplenishStockValidateForm()
	{
		FormValidator val = new FormValidator();

		String testQuantityString = null;
		String msg;
		try
		{
			msg = val.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.BLANK_REPLENISH_QUANTITY));
			
			testQuantityString = "";
			msg = val.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.BLANK_REPLENISH_QUANTITY));
			
			testQuantityString = "-1";
			msg = val.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.INVALID_REPLENISH_QUANTITY));
			
			testQuantityString = "0";
			msg = val.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.INVALID_REPLENISH_QUANTITY));
					
			testQuantityString = "test";
			msg = val.replenishStockValidateForm(testQuantityString);
			assertTrue(msg.contains(StoreConstants.INVALID_REPLENISH_QUANTITY));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			val = null;
		}
	}

}
