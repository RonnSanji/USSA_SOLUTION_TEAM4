package sg.edu.nus.iss.ssa.validation;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

public class FormValidator {

	/*
	 * Naming convention rule: form name + ValidateForm
	 * No UI controller
	 * 
	 */
	public String addCategoryValidateForm(String categoryID, String categoryName) {

		if (categoryID == null || categoryID.isEmpty()) {
			return StoreConstants.BLANK_CATEGORYID;
		}
		categoryID = categoryID.trim().toUpperCase();
		if (categoryID.length() != 3) {
			return StoreConstants.CATEGORY_3_LETTERS;
		}
		categoryName = categoryName.trim();
		if (categoryName == null || categoryName.isEmpty()) {
			return StoreConstants.BLANK_CATEGORYNAME;
		}

		return null;
	}
	
	public String addCategoryValidateData()
	{
		return null;
	}
}
