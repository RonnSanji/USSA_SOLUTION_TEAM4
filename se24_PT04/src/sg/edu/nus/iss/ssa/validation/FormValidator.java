package sg.edu.nus.iss.ssa.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.StoreKeeper;
import sg.edu.nus.iss.ssa.model.Vendor;

public class FormValidator {

	/*
	 * Naming convention rule: form name + ValidateForm No UI controller
	 * 
	 */
	public static String addEditCategoryValidateForm(String categoryID, String categoryName) {
		if (categoryID == null) {
			return StoreConstants.ENTER_CATEGORYID;
		}
		categoryID = categoryID.trim().toUpperCase();
		if (categoryID.isEmpty()) {
			return StoreConstants.ENTER_CATEGORYID;
		}
		if (categoryID.contains(",") || categoryID.contains("\n")) {
			return StoreConstants.INVALID_CATEGORYID;
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
		if (categoryName.contains(",") || categoryName.contains("\n")) {
			return StoreConstants.INVALID_CATEGORYNAME;
		}
		return null;
	}

	public static String addCategoryValidateData(Category selectedCategory) {
		if (selectedCategory == null) {
			return StoreConstants.EMPTY_CATEGORY;
		}

		for (Category category : FileDataWrapper.categoryMap.values()) {
			if (category.getCategoryId().equalsIgnoreCase(selectedCategory.getCategoryId())) {
				return "Category ID: " + selectedCategory.getCategoryId() + " " + StoreConstants.CATEGORY_EXISTS;
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

	public static String editRemoveCategoryValidateData(Category selectedCategory) {
		if (selectedCategory == null) {
			return StoreConstants.EMPTY_CATEGORY;
		}
		for (Category category : FileDataWrapper.categoryMap.values()) {
			if (category.getCategoryId().equalsIgnoreCase(selectedCategory.getCategoryId())) {
				return null;
			}
		}

		return "Category ID: " + selectedCategory.getCategoryId() + " " + StoreConstants.CATEGORYID_NOT_EXIST;
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

	public static String writeOffStockValidateForm(long curentQuantity, String stockTxt) {

		if (stockTxt == null || stockTxt.isEmpty()) {
			return StoreConstants.ENTER_WRITEOFF_QUANTITY;
		}
		try {
			long l = Long.parseLong(stockTxt);
			if (l <= 0) {
				return StoreConstants.INVALID_WRITEOFF_QUANTITY;
			}
			if (l > curentQuantity) {
				return StoreConstants.INVALID_WRITEOFF_QUANTITY;
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return StoreConstants.INVALID_WRITEOFF_QUANTITY;
		}
		return null;
	}

	public static String configureThresholdReorderQuantityValidateForm(String threshold, String reorderQuantity) {

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
		if (reorderQuantity == null || reorderQuantity.isEmpty()) {
			return StoreConstants.ENTER_NEW_REORDER_QUANTITY;
		}
		try {
			long l = Long.parseLong(reorderQuantity);
			if (l <= 0) {
				return StoreConstants.INVALID_REORDER_QUANTITY;
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return StoreConstants.INVALID_REORDER_QUANTITY;
		}

		return null;
	}

	public static String replenishStockConfigureThresholdValidateData(int barCode) {
		if (barCode <= 0) {
			return StoreConstants.INVALID_PRODUCT_BAR_CODE;
		}
		for (Product product : FileDataWrapper.productMap.values()) {
			if (product.getBarCode() == barCode) {
				return null;
			}
		}
		return "Bar code: " + barCode + " " + StoreConstants.BAR_CODE_NOT_EXIST;
	}

	public static String addEditDiscountValidateForm(String discountCode, String discountDescription,
			String startDateType, Date startDate, String period, String percentage, String applicableTo) {
		if (discountCode == null || discountCode.isEmpty()) {
			return StoreConstants.ENTER_DISCOUNT_CODE;
		}
		if (discountCode.contains(",") || discountCode.contains("\n")) {
			return StoreConstants.INVALID_DISCOUNT_CODE;
		}
		if (discountDescription == null || discountDescription.isEmpty()) {
			return StoreConstants.ENTER_DISCOUNT_DESCRIPTION;
		}
		if (discountDescription.contains(",") || discountDescription.contains("\n")) {
			return StoreConstants.INVALID_DISCOUNT_DESCRIPTION;
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
		if (period.contains(",") || period.contains("\n")) {
			return StoreConstants.INVALID_DISCOUNT_PERIOD;
		}
		if (startDateType.equalsIgnoreCase(StoreConstants.PERMANENT_DSCOUNT_START_DATE)) {
			if (!period.equalsIgnoreCase(StoreConstants.PERMANENT_DSCOUNT_START_PERIOD)) {
				return StoreConstants.INVALID_DISCOUNT_PERIOD;
			}
		} else if (startDateType.equalsIgnoreCase(StoreConstants.PERIOD_DSCOUNT_START_DATE)) {
			try {
				Integer tempPeriod = Integer.parseInt(period);
				if (tempPeriod <= 0) {
					return StoreConstants.INVALID_DISCOUNT_PERIOD;
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
				return StoreConstants.INVALID_DISCOUNT_PERIOD;
			}
		}
		if (percentage == null || percentage.isEmpty()) {
			return StoreConstants.ENTER_DISCOUNT_PERCENTAGE;
		}
		try {
			float tempPercentage = Float.parseFloat(percentage);
			if (tempPercentage <= 0 || tempPercentage >= 100) {
				return StoreConstants.INVALID_DISCOUNT_PERCENTAGE;
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return StoreConstants.INVALID_DISCOUNT_PERCENTAGE;
		}
		if (applicableTo == null || applicableTo.isEmpty()) {
			return StoreConstants.SELECT_DISCOUNT_APPLICABLE_TO;
		}
		if (applicableTo.equalsIgnoreCase("-select-")) {
			return StoreConstants.SELECT_DISCOUNT_APPLICABLE_TO;
		}
		if (!applicableTo.equalsIgnoreCase(StoreConstants.MEMBER_DICSOUNT_NAME)
				&& !applicableTo.equalsIgnoreCase(StoreConstants.PUBLIC_DICSOUNT_NAME)) {
			return StoreConstants.INVALID_APPLICABLE_TO;
		}
		return null;
	}

	public static String addDiscountValidateData(PeriodDiscount selectedDiscount) {
		if (selectedDiscount == null) {
			return StoreConstants.EMPTY_DISCOUNT;
		}
		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			if (tempDiscount.getDiscountCode().equalsIgnoreCase(selectedDiscount.getDiscountCode())) {
				return "Discount: " + selectedDiscount.getDiscountCode() + " " + StoreConstants.DISCOUNT_EXIST;
			}
		}
		return null;
	}

	public static String editRemoveDiscountValidateData(PeriodDiscount selectedDiscount) {
		if (selectedDiscount == null) {
			return StoreConstants.EMPTY_DISCOUNT;
		}
		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			if (tempDiscount.getDiscountCode().equalsIgnoreCase(selectedDiscount.getDiscountCode())) {
				return null;
			}
		}
		return "Discount: " + selectedDiscount.getDiscountCode() + " " + StoreConstants.DISCOUNT_NOT_EXIST;
	}

	public static String addMemberValidateForm(String memberName, String memberNumber) {
		if (memberName == null || memberName.isEmpty()) {
			return StoreConstants.BLANK_MEMBER_NAME;
		}
		if (memberName.contains(",") || memberName.contains("\n")) {
			return StoreConstants.INVALID_NEWMEMBER_NAME;
		}
		if (memberNumber == null || memberNumber.isEmpty()) {
			return StoreConstants.BLANK_MEMBER_NUMBER;
		}
		if (memberNumber.length() != 9) {
			return StoreConstants.INVALID_NEWMEMBER_NUMBER;
		}
		if (memberNumber.contains(",") || memberNumber.contains("\n")) {
			return StoreConstants.INVALID_NEWMEMBER_NUMBER;
		}
		return null;
	}

	public static String editMemeberValidateForm(String memberName, String memberNumber, long LPoint) {
		if (memberName == null || memberName.isEmpty()) {
			return StoreConstants.BLANK_MEMBER_NAME;
		}
		if (memberName.contains(",") || memberName.contains("\n")) {
			return StoreConstants.INVALID_NEWMEMBER_NAME;
		}
		if (memberNumber == null || memberNumber.isEmpty()) {
			return StoreConstants.BLANK_MEMBER_NUMBER;
		}
		if (memberNumber.length() != 9) {
			return StoreConstants.INVALID_NEWMEMBER_NUMBER;
		}
		if (memberNumber.contains(",") || memberNumber.contains("\n")) {
			return StoreConstants.INVALID_NEWMEMBER_NUMBER;
		}
		if (LPoint < 0) {
			return StoreConstants.INVALID_LOYLTY_POINT;
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
		// System.out.println(FileDataWrapper.storeKeeperMap.values());
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

	public static String addProductValidateForm(String categoryName, String productName, String productDescription,
			String quantityAvailable, String price, String thresholdQuantity, String reorderQuantity) {
		if (categoryName == null || categoryName.isEmpty()) {
			return StoreConstants.BLANK_CATEGORYNAME;
		}
		if (categoryName.contains(",") || categoryName.contains("\n")) {
			return StoreConstants.INVALID_CATERORY;
		}
		if (categoryName.length() != 3) {
			return StoreConstants.INVALID_CATERORY;
		}

		if (productName == null || productName.isEmpty()) {
			return StoreConstants.EMPTY_PRODUCT_NAME;
		}
		if (productName.contains(",") || productName.contains("\n")) {
			return StoreConstants.INVALID_PRODUCT_NAME;
		}

		if (productDescription == null || productDescription.isEmpty()) {
			return StoreConstants.EMPTY_PRODUCT_DESCRIPTION;
		}
		if (productDescription.contains(",") || productDescription.contains("\n")) {
			return StoreConstants.INVALID_PRODUCT_DESCRIPTION;
		}

		if (quantityAvailable == null || quantityAvailable.isEmpty()) {
			return StoreConstants.EMPTY_QUANTITY;
		}
		try {
			int i = Integer.parseInt(quantityAvailable);
			if (i < 0) {
				return StoreConstants.INVALID_QUANTITY;
			}
		} catch (Exception ex) {
			return StoreConstants.INVALID_QUANTITY;
		}

		if (price == null || price.isEmpty()) {
			return StoreConstants.EMPTY_PRICE;
		}
		try {
			double d = Double.parseDouble(price);
			if (d <= 0) {
				return StoreConstants.INVALID_PRICE;
			}
		} catch (Exception ex) {
			return StoreConstants.INVALID_PRICE;
		}

		if (thresholdQuantity == null || thresholdQuantity.isEmpty()) {
			return StoreConstants.EMPTY_THRESHOLD;
		}
		try {
			int i = Integer.parseInt(thresholdQuantity);
			if (i <= 0) {
				return StoreConstants.INVALID_THRESHOLD;
			}
		} catch (Exception ex) {
			return StoreConstants.INVALID_THRESHOLD;
		}

		if (reorderQuantity == null || reorderQuantity.isEmpty()) {
			return StoreConstants.EMPTY_REORDER_QUANTITY;
		}
		try {
			int i = Integer.parseInt(reorderQuantity);
			if (i <= 0) {
				return StoreConstants.INVALID_REORDER_QUANTITY;
			}
		} catch (Exception ex) {
			return StoreConstants.INVALID_REORDER_QUANTITY;
		}

		return null;
	}

	public static String addEditVendorValidateForm(String vendorCode, String vendorName) {
		if (vendorCode == null || vendorCode.isEmpty()) {
			return StoreConstants.ENTER_VENDOR_CODE;
		}
		if (vendorCode.contains(",") || vendorCode.contains("\n")) {
			return StoreConstants.INVALID_VENDOR_CODE;
		}
		if (vendorName == null || vendorName.isEmpty()) {
			return StoreConstants.ENTER_VENDOR_NAME;
		}
		if (vendorName.contains(",") || vendorName.contains("\n")) {
			return StoreConstants.INVALID_VENDOR_NAME;
		}
		return null;
	}

	public static String removeVendorValidateForm(String vendorCode) {
		if (vendorCode == null || vendorCode.isEmpty()) {
			return StoreConstants.SELECT_VENDOR;
		}

		return null;
	}

	public static String editRemoveVendorValidateData(Vendor selectedVendor) {
		if (selectedVendor == null) {
			return StoreConstants.EMPTY_VENDOR;
		}
		for (int i = 0; i < FileDataWrapper.vendorList.size(); i++) {
			Vendor tempVendor = FileDataWrapper.vendorList.get(i);
			if (tempVendor.getVendorId().equalsIgnoreCase(selectedVendor.getVendorId())) {
				return null;
			}
		}
		return "Vendor: " + selectedVendor.getVendorId() + " " + StoreConstants.VENDOR_NOT_EXIST;
	}

	public static String addVendorValidateData(Vendor selectedVendor) {
		if (selectedVendor == null) {
			return StoreConstants.EMPTY_VENDOR;
		}
		for (int i = 0; i < FileDataWrapper.vendorList.size(); i++) {
			Vendor tempVendor = FileDataWrapper.vendorList.get(i);
			if (tempVendor.getVendorId().equalsIgnoreCase(selectedVendor.getVendorId())) {
				return "Vendor: " + selectedVendor.getVendorId() + " " + StoreConstants.VENDOR_EXIST;
			}
		}
		return null;
	}

	public static String editProductValidateForm(String productName, String productDescription, Double price) {
		if (productName == null || productName.isEmpty()) {
			return StoreConstants.EMPTY_PRODUCT_NAME;
		}
		if (productName.contains(",") || productName.contains("\n")) {
			return StoreConstants.INVALID_PRODUCT_NAME;
		}

		if (productDescription == null || productDescription.isEmpty()) {
			return StoreConstants.EMPTY_PRODUCT_DESCRIPTION;
		}
		if (productDescription.contains(",") || productDescription.contains("\n")) {
			return StoreConstants.INVALID_PRODUCT_DESCRIPTION;
		}
		if (price <= 0) {
			return StoreConstants.INVALID_PRICE;
		}
		
		return null;
	}
}
