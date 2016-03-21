package sg.edu.nus.iss.ssa.validation;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

public class FormValidator
{

	/*
	 * Naming convention rule: form name + ValidateForm No UI controller
	 * 
	 */
	public String addCategoryValidateForm(String categoryID, String categoryName)
	{
		if (categoryID == null)
		{
			return StoreConstants.BLANK_CATEGORYID;
		}
		categoryID = categoryID.trim().toUpperCase();
		if (categoryID.isEmpty())
		{
			return StoreConstants.BLANK_CATEGORYID;
		}
		if (categoryID.length() != 3)
		{
			return StoreConstants.CATEGORY_3_LETTERS;
		}
		if (categoryName == null)
		{
			return StoreConstants.BLANK_CATEGORYNAME;
		}
		categoryName = categoryName.trim();
		if (categoryName.isEmpty())
		{
			return StoreConstants.BLANK_CATEGORYNAME;
		}

		return null;
	}

	public String addCategoryValidateData(String categoryID)
	{
		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		ArrayList<String> tempKeyList = new ArrayList<>();

		if (keySet != null && keySet.size() > 0)
		{
			for (String key : keySet)
			{
				tempKeyList.add(key.toUpperCase());
			}
			if (tempKeyList.contains(categoryID.toUpperCase()))
			{
				return "Category ID: " + categoryID + " " + StoreConstants.CATEGORY_EXISTS;
			}
		}
		return null;
	}

	public String replenishStockValidateForm(String stockTxt)
	{

		if (stockTxt == null || stockTxt.isEmpty())
		{
			return StoreConstants.BLANK_REPLENISH_QUANTITY;
		}
		try
		{
			long l = Long.parseLong(stockTxt);
			if (l <= 0)
			{
				return StoreConstants.INVALID_REPLENISH_QUANTITY;
			}
		}
		catch (Exception ex)
		{
			//ex.printStackTrace();
			return StoreConstants.INVALID_REPLENISH_QUANTITY;
		}
		return null;
	}

	public String addMemberValidateForm(String memberName, String memberNumber)
	{

		if (memberName == null || memberNumber.isEmpty())
		{
			return StoreConstants.BLANK_MEMBER_NUMBERANDNAME;
		}

		if (memberNumber.length() != 9)
		{
			return StoreConstants.INVALID_NEWMEMBER_NUMBER;
		}

		return null;
	}

}
