package sg.edu.nus.iss.ssa.constants;

public class StoreConstants {

	public static final String PROJECT_NAME = "se24_PT04";
	public static final String FIELD_DELIMITER = ",";
	public static final int CASH_EQ_POINTS = 100; // 100 points equal to 1 $
	public static final int POINTS_FOR_CASH = 1; // every 1$ spent will earn 1
													// point
	public static final int CATEGORY_ID_MAX_LENGTH = 3;
	public static final int MEMBER_NUMBER_LENGTH = 9;
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String PUBLIC_USER = "PUBLIC";
	public static final String MEMBER_FIRST_DISCOUNT_CODE = "MEMBER_FIRST";
	public static final String MEMBER_SUBSEQ_CODE = "MEMBER_SUBSEQ";
	public static final int PERIOD_BACKWARD_YEAR = 10;
	public static final int PERIOD_FORWARD_YEAR = 10;
	public static final String PERMANENT_DSCOUNT_START_DATE = "ALWAYS";
	public static final String PERIOD_DSCOUNT_START_DATE = "Period";
	public static final String PERMANENT_DSCOUNT_START_PERIOD = "ALWAYS";
	public static final String MEMBER_DICSOUNT_CODE = "M";
	public static final String MEMBER_DICSOUNT_NAME = "Member";
	public static final String PUBLIC_DICSOUNT_CODE = "A";
	public static final String PUBLIC_DICSOUNT_NAME = "All";

	public static final String INVALID_PRODUCT = "Please Select a valid product.";
	public static final String INVALID_DATE = "Please Select a valid date.";
	public static final String BLANK_INPUT_FOR_PRODUCT_PURCHASE = "Please enter BarCode and Quantity to purchase the product.";
	public static final String PRODUCT_QUANTITY_NON_NUMERIC = "Quantity must be numeric value.";
	public static final String INVALID_PRODUCT_QUANTITY = "Entered Product Quantity is higher than available Stock.";
	public static final String INVALID_MEMBER_POINT ="Please enter a valid member point";
	public static final String INVALID_CASH ="Please enter a valid cash amount";

	// Member Validation error message
	public static final String BLANK_MEMBER_NUMBER = "Please enter Member Number.";
	public static final String INVALID_MEMBER_NUMBER = "Not a valid member.";

	// payment validation
	public static final String REQ_PAYMENT_FIELDS = "Please enter Cash or points to process the Payment.";
	public static final String POINTS_REDEEMED_CONSTRAINT = "Points can be redeemed in multiple of 100.";
	public static final String MAX_POINTS_REDEEMED_MSG = "There is no enough points to redeem. Available points are : ";

	public static final String NOT_ENOUGH_CASH = "Not enough amount to process the payment. ";

	// Manage category message
	public static final String CATEGORY_ADDED_SUCCESSFULLY = "Category has been added successfully. Would like to add another one ?";
	public static final String PRODUCT_ADDED_SUCCESSFULLY = "Product has been added successfully. Would like to add another one ?";
	public static final String CATEGORY_EXISTS = "already exists";
	public static final String ENTER_CATEGORYID = "Please enter category ID";
	public static final String INVALID_CATEGORYID = "Please enter a valid category ID";
	public static final String ENTER_CATEGORYNAME = "Please enter category name";
	public static final String INVALID_CATEGORYNAME = "Please enter a valid category name";
	public static final String BLANK_CATEGORYID = "Category ID is blank !";
	public static final String BLANK_CATEGORYNAME = "Category name is blank !";
	public static final String CATEGORY_3_LETTERS = "Category ID must be 3 letters";
	public static final String ERROR = "Error occured during";
	public static final String SELECT_CATEGORY = "Please select a category";
	public static final String CATEGORY_REMOVED_SUCCESSFULLY = "Category has been removed successfully !";
	public static final String CATEGORYID_NOT_EXIST = "does not exist";
	public static final String CONFIRM_TO_REMOVE_CATEROGY = "Confirm to remove category ?";
	public static final String EMPTY_CATEGORY = "Empty category !";
	public static final String CATEGORY_UPDATED_SUCCESSFULLY = "Category has been updated successfully !";

	// Manage Member message
	public static final String MEMBER_ADDED_SUCCESSFULLY = "Member has been added succesfully";
	public static final String BLANK_MEMBER_NAME = "Please enter Member Name";
	public static final String ENTER_MEMBER_NUMBER = "Please enter memeber number";
	public static final String INVALID_NEWMEMBER_NUMBER = "Not a valid member, Please enter a number with 9 Charactor";
	public static final String INVALID_NEWMEMBER_NAME = "Please enter a valid member name";
	public static final String INVALID_LOYLTY_POINT = "Please enter a valid loylty point";
	public static final String SELECT_MEMBER = "Please select a member";
	public static final String CONFIRM_REMOVE_MEMBER = "Confirm to remove this member ?";
	public static final String ENTER_MEMBER_NAME = "Please enter memeber name";
	public static final String MEMBER_UPDATED_SUCCESSFULLY = "Member has been updated successfully !";
	public static final String MEMBER_REMOVED_SUCCESSFULLY = "Member has been removed successfully !";
	
	
	// Manage stock message
	public static final String SELECT_PRODUCT = "Please select a product";
	public static final String STOCK_UPDATED_SUCCESSFULLY = "Stock has been updated successfully !";
	public static final String ENTER_REPLENISH_QUANTITY = "Please enter replenish quantity";
	public static final String ENTER_WRITEOFF_QUANTITY = "Please enter write off quantity";
	public static final String BLANK_REPLENISH_QUANTITY = "Replenish quantity is empty !";
	public static final String BLANK_PRODUCT_BAR_CODE = "Bar code is blank !";
	public static final String INVALID_PRODUCT_BAR_CODE = "is not a valid bar code !";
	public static final String INVALID_REPLENISH_QUANTITY = "Please enter a valid replenish quantity";
	public static final String INVALID_WRITEOFF_QUANTITY = "Please enter a valid write off quantity";
	public static final String BAR_CODE_NOT_EXIST = "does not exist";
	public static final String ENTER_NEW_THRESHOLD = "Please enter new threshold quantity";
	public static final String ENTER_NEW_REORDER_QUANTITY = "Please enter new reorder quantity";
	public static final String INVALID_THRESHOLD_QUANTITY = "Please enter a valid threshold quantity";
	public static final String INVALID_REORDER_QUANTITY = "Please enter a valid reorder quantity";
	public static final String THRESHOLD_REORDER_QUANTITY_UPDATED_SUCCESSFULLY = "Threshold/Reorder quantity has been updated successfully !";

	// manage Product Message
	public static final String EMPTY_QUANTITY = "Please Enter the Quantity greater than 0";
	public static final String EMPTY_PRICE = "Please Enter the Price greater than 0";
	public static final String EMPTY_THRESHOLD = "Please Enter the Threshold greater than 0";
	public static final String EMPTY_REORDER_QUANTITY = "Please Enter the reorder quantity greater than 0";
	public static final String EMPTY_PRODUCT_NAME = "Please Enter the Product Name. Product Name cannot be Empty";
	public static final String EMPTY_PRODUCT_DESCRIPTION = "Please Enter the Product Description";
	public static final String INVALID_PRODUCT_NAME = "Please Enter a valid Product Name";
	public static final String INVALID_PRODUCT_DESCRIPTION = "Please Enter a valid Product Description";
	public static final String NUMBER_REGEX = "[+-]?\\d*(\\.\\d+)?";
	public static final String STRING_REGEX = "[a-zA-Z]";
	public static final String INVALID_CATERORY = "Invalid Category, Please select a valid one";
	public static final String INVALID_QUANTITY = "Invalid Quantity Entered. Please Enter a valid one.";
	public static final String INVALID_PRICE = "Invalid Product Price. Please Enter only number.";
	public static final String INVALID_THRESHOLD = "Invalid Product Threshold Quantity. Please Enter Only Digits.";
	public static final String CONFIRM_REMOVE_PRODUCT = "Confirm to remove this product ?";
	public static final String ENTER_PRINT_COPY = "Please enter the number of copies to print";
	public static final String INVALID_PRINT_COPY = "Please enter a valid number of copies to print";
	public static final String PRODUCT_UPDATED_SUCCESSFULLY = "Product has been updated successfully !";
	public static final String PRODUCT_REMOVED_SUCCESSFULLY = "Product has been removed successfully !";
	public static final String PRODUCT_ADDED_SUCCESSFULLY_ = "Product has been added successfully !";
	
	// StoreKeeper validation message
	public static final String STOREKEEPER_NOT_FOUND = "StoreKeeper not found.";
	public static final String STOREKEEPER_INCORRECT_PASSWORD = "Incorrect password.";

	// Manage discount message
	public static final String SELECT_DISCOUNT = "Please select a discount";
	public static final String ENTER_DISCOUNT_CODE = "Please enter discount code";
	public static final String INVALID_DISCOUNT_CODE = "Please enter a valid discount code";
	public static final String ENTER_DISCOUNT_DESCRIPTION = "Please enter discount description";
	public static final String INVALID_DISCOUNT_DESCRIPTION = "Please enter a valid discount description";
	public static final String SELECT_DISCOUNT_START_DATE = "Please select discount start date";
	public static final String ENTER_DISCOUNT_PERIOD = "Please enter discount period";
	public static final String ENTER_DISCOUNT_PERCENTAGE = "Please enter discount percentage";
	public static final String SELECT_DISCOUNT_APPLICABLE_TO = "Please select Applicable To";
	public static final String INVALID_DISCOUNT_START_DATE = "Discount start date is invalid";
	public static final String INVALID_DISCOUNT_PERIOD = "Discount period is invalid";
	public static final String INVALID_DISCOUNT_PERCENTAGE = "Discount percentage is invalid";
	public static final String INVALID_APPLICABLE_TO = "Applicable To is invalid";
	public static final String DISCOUNT_UPDATED_SUCCESSFULLY = "Discount has been updated successfully !";
	public static final String DISCOUNT_ADDED_SUCCESSFULLY = "Discount has been added successfully. Would like to add another one ?";
	public static final String DISCOUNT_REMOVED_SUCCESSFULLY = "Discount has been removed successfully !";
	public static final String EMPTY_DISCOUNT = "Empty discount !";
	public static final String DISCOUNT_NOT_EXIST = "does not exist";
	public static final String DISCOUNT_EXIST = "already exists";

	// manage vendor
	public static final String SELECT_VENDOR = "Please select a vendor";
	public static final String VENDOR_REMOVED_SUCCESSFULLY = "Vendor has been removed successfully !";
	public static final String VENDOR_LIST_EMPTY = "Vendor list is empty, please add vendor";
	public static final String VENDOR_ADDED_SUCCESSFULLY = "Vendor has been added successfully. Would like to add another one ?";
	public static final String VENDOR_UPDATED_SUCCESSFULLY = "Vendor has been updated successfully !";
	public static final String ENTER_VENDOR_CODE = "Please enter vendor code";
	public static final String INVALID_VENDOR_CODE = "Please enter a valid vendor code";
	public static final String ENTER_VENDOR_NAME = "Please enter vendor name";
	public static final String INVALID_VENDOR_NAME = "Please enter a valid vendor name";
	public static final String EMPTY_VENDOR = "Empty vendor !";
	public static final String VENDOR_EXIST = "already exists";
	public static final String VENDOR_NOT_EXIST = "does not exist";
	public static final String CONFIRM_TO_REMOVE_VENDOR = "Confirm to remove vendor ?";
	public static final String NO_VENDOR_CONFIGURED = "No vendor configured";

	public static final String BLANK_PRODUCT_DESCRIPTIONANDNAME = "Please Enter production description and name";
	public static final String  INVALID_NEWPRODUCT_NAME = "Invalid New Product Name";
	public static final String INVALID_NEWPRODUCT_DESCRIPTION = "Invalid new product description ";
}
