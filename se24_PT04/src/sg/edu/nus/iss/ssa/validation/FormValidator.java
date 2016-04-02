package sg.edu.nus.iss.ssa.validation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.StoreKeeper;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;

public class FormValidator {

	/*
	 * Naming convention rule: form name + ValidateForm No UI controller
	 * 
	 */
	public static String addCategoryValidateForm(String categoryID, String categoryName) {
		if (categoryID == null) {
			return StoreConstants.ENTER_CATEGORYID;
		}
		categoryID = categoryID.trim().toUpperCase();
		if (categoryID.isEmpty()) {
			return StoreConstants.ENTER_CATEGORYID;
		}
		if (categoryID.length() != 3) {
			return StoreConstants.CATEGORY_3_LETTERS;
		}
		if (categoryName == null) {
			return StoreConstants.ENTER_CATEGORYNAME;
		}
		categoryName = categoryName.trim();
		if (categoryName.isEmpty()) {
			return StoreConstants.ENTER_CATEGORYNAME;
		}

		return null;
	}

	public static String addCategoryValidateData(String categoryID) {
		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		ArrayList<String> tempKeyList = new ArrayList<>();

		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				tempKeyList.add(key.toUpperCase());
			}
			if (tempKeyList.contains(categoryID.toUpperCase())) {
				return "Category ID: " + categoryID + " " + StoreConstants.CATEGORY_EXISTS;
			}
		}
		return null;
	}

	public static String removeCategoryValidateForm(String categoryID) {
		if (categoryID == null || categoryID.isEmpty()) {
			return StoreConstants.SELECT_CATEGORY;
		}
		return null;
	}

	public static String removeCategoryValidateData(String categoryID) {
		if (categoryID == null || categoryID.isEmpty()) {
			return StoreConstants.SELECT_CATEGORY;
		}
		try {
			EntityListController controller = new EntityListController();
			controller.reloadCategoryData();
			controller = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return StoreConstants.ERROR + "reloading category";
		}
		categoryID = categoryID.trim();
		Set<String> keys = FileDataWrapper.categoryMap.keySet();
		if (!keys.contains(categoryID)) {
			return categoryID + " " + StoreConstants.CATEGORYID_NOT_EXIST;
		}
		return null;
	}

	public static String replenishStockValidateForm(String stockTxt) {

		if (stockTxt == null || stockTxt.isEmpty()) {
			return StoreConstants.ENTER_REPLENISH_QUANTITY;
		}
		try {
			long l = Long.parseLong(stockTxt);
			if (l <= 0) {
				return StoreConstants.INVALID_REPLENISH_QUANTITY;
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return StoreConstants.INVALID_REPLENISH_QUANTITY;
		}
		return null;
	}

	public static String configureThresholdValidateForm(String threshold) {

		if (threshold == null || threshold.isEmpty()) {
			return StoreConstants.ENTER_NEW_THRESHOLD;
		}
		try {
			long l = Long.parseLong(threshold);
			if (l <= 0) {
				return StoreConstants.INVALID_THRESHOLD_QUANTITY;
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return StoreConstants.INVALID_THRESHOLD_QUANTITY;
		}
		return null;
	}

	public static String replenishStockConfigureThresholdValidateData(int barCode) {
		if (barCode <= 0) {
			return StoreConstants.INVALID_PRODUCT_BAR_CODE;
		}
		try {
			EntityListController controller = new EntityListController();
			controller.reloadCategoryData();
			controller = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return StoreConstants.ERROR + "reloading product";
		}
		for (Product product : FileDataWrapper.productMap.values()) {
			if (product.getBarCode() == barCode) {
				return null;
			}
		}
		return barCode + " " + StoreConstants.BAR_CODE_NOT_EXIST;
	}

	public static String editDscountValidateForm(String discountCode, String discountDescription, String startDateType,
			Date startDate, String period, String percentage, String applicableTo) {
		if (discountCode == null || discountCode.isEmpty()) {
			return StoreConstants.ENTER_DISCOUNT_CODE;
		}
		if (discountDescription == null || discountDescription.isEmpty()) {
			return StoreConstants.ENTER_DISCOUNT_DESCRIPTION;
		}
		if (startDateType == null || startDateType.isEmpty()) {
			return StoreConstants.SELECT_DISCOUNT_START_DATE;
		}
		if (!startDateType.equalsIgnoreCase(StoreConstants.PERMANENT_DSCOUNT_START_DATE)
				&& !startDateType.equalsIgnoreCase(StoreConstants.PERIOD_DSCOUNT_START_DATE)) {
			return StoreConstants.SELECT_DISCOUNT_START_DATE;
		}
		if (startDateType.equalsIgnoreCase(StoreConstants.PERIOD_DSCOUNT_START_DATE)) {
			if (startDate == null) {
				return StoreConstants.SELECT_DISCOUNT_START_DATE;
			}
		}
		if (period == null || period.isEmpty()) {
			return StoreConstants.ENTER_DISCOUNT_PERIOD;
		}
		if (startDateType.equalsIgnoreCase(StoreConstants.PERMANENT_DSCOUNT_START_DATE)) {
			if (!period.equalsIgnoreCase(StoreConstants.PERMANENT_DSCOUNT_START_PERIOD)) {
				return StoreConstants.INVALID_DISCOUNT_PERIOD;
			}
		} else if (startDateType.equalsIgnoreCase(StoreConstants.PERIOD_DSCOUNT_START_DATE)) {
			try {
				Integer tempPeriod = Integer.parseInt(period);
			} catch (Exception ex) {
				// ex.printStackTrace();
				return StoreConstants.INVALID_DISCOUNT_PERIOD;
			}
		}
		if (percentage == null || percentage.isEmpty()) {
			return StoreConstants.ENTER_DISCOUNT_PERCENTAGE;
		}
		if (applicableTo == null || applicableTo.isEmpty()) {
			return StoreConstants.SELECT_DISCOUNT_APPLICABLE_TO;
		}
		if (applicableTo.equalsIgnoreCase("-select-")) {
			return StoreConstants.SELECT_DISCOUNT_APPLICABLE_TO;
		}

		try {
			float tempPercentage = Float.parseFloat(percentage);
			if(tempPercentage <=0 || tempPercentage >=100)
			{
				return StoreConstants.INVALID_DISCOUNT_PERCENTAGE;
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return StoreConstants.INVALID_DISCOUNT_PERCENTAGE;
		}
		if (!applicableTo.equalsIgnoreCase(StoreConstants.MEMBER_DICSOUNT_NAME)
				&& !applicableTo.equalsIgnoreCase(StoreConstants.PUBLIC_DICSOUNT_NAME)) {
			return StoreConstants.INVALID_APPLICABLE_TO;
		}
		return null;
	}

	public static String addMemberValidateForm(String memberName, String memberNumber) {
		if (memberName == null || memberNumber.isEmpty()) {
			return StoreConstants.BLANK_MEMBER_NUMBERANDNAME;
		}
		if (memberNumber.length() != 9) {
			return StoreConstants.INVALID_NEWMEMBER_NUMBER;
		}
		return null;
	}

	public static String addStoreKeeperValidateForm(String name, char[] password) {
		if (name == null || name.isEmpty()) {
			return StoreConstants.STOREKEEPER_NOT_FOUND;
		}

		if (password == null || password.length == 0) {
			return StoreConstants.STOREKEEPER_INCORRECT_PASSWORD;
		}
		System.out.println(FileDataWrapper.storeKeeperMap.values());
		StoreKeeper storeKeeper = (StoreKeeper) FileDataWrapper.storeKeeperMap.get(name.toLowerCase());
		if (storeKeeper == null) {
			// System.out.println("NONONONONO");
			return StoreConstants.STOREKEEPER_NOT_FOUND;
		}

		char[] desiredPassword = storeKeeper.getPassword().toCharArray();
		if (desiredPassword.length != password.length || !Arrays.equals(password, desiredPassword)) {
			return StoreConstants.STOREKEEPER_INCORRECT_PASSWORD;
		}

		return null;
	}

	public static String addProductValidateForm(String categoryName, String productName, String quantityAvailable,
			String price, String thresholdQuantity) {
		if (categoryName == null || categoryName.isEmpty()) {
			return StoreConstants.BLANK_CATEGORYNAME;
		} else if (quantityAvailable.isEmpty() || quantityAvailable == null) {
			return StoreConstants.EMPTY_QUANTITY;
		} else if (price.isEmpty() || price == null) {
			return StoreConstants.EMPTY_PRICE;
		} else if (productName == null || productName.isEmpty()) {
			return StoreConstants.EMPTY_PRODUCT_NAME;
		} else if (categoryName.length() != 3) {
			return "Invalid Category, Please select a valid one";
		} else if (!quantityAvailable.matches(StoreConstants.NUMBER_REGEX)) {
			return "Invalid Quantity Entered. Please Enter a valid one.";
		} else if (!(categoryName.matches(StoreConstants.STRING_REGEX))) {
			return "Invalid Category Name. Please Enter only characters";
		} else if (!(price.matches(StoreConstants.NUMBER_REGEX))) {
			return "Invalid Product Price. Please Enter only number.";
		} else if (!(productName.matches(StoreConstants.STRING_REGEX))) {
			return "Invalid Product Name. Please Enter a correct name.";
		} else if (!(thresholdQuantity.matches(StoreConstants.NUMBER_REGEX))) {
			return "Invalid Product Threshold Quantity. Please Enter Only Digits.";
		}
		return null;
	}
}
