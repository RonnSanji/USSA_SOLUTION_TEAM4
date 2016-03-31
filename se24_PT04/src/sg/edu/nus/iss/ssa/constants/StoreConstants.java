package sg.edu.nus.iss.ssa.constants;

public class StoreConstants {

	public static final String PROJECT_NAME = "se24_PT04";
	public static final String FIELD_DELIMITER = ",";
	public static final int CASH_EQ_POINTS = 100; // 100 points equal to 1 $
	public static final int POINTS_FOR_CASH = 1; // every 1$ spent will earn 1
													// point
	public static final int CATEGORY_ID_MAX_LENGTH = 3;
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String PUBLIC_USER = "PUBLIC";
	public static final String[] FIXED_DISCOUNT = new String[] { "MEMBER_FIRST", "MEMBER_SUBSEQ" };
	public static final int PERIOD_BACKWARD_YEAR = 0;
	public static final int PERIOD_FORWARD_YEAR = 10;

	public static final String INVALID_PRODUCT = "Please Select a valid product.";
	public static final String INVALID_DATE = "Please Select a valid date.";
	public static final String BLANK_INPUT_FOR_PRODUCT_PURCHASE = "Please enter BarCode and Quantity to purchase the product.";
	public static final String PRODUCT_QUANTITY_NON_NUMERIC = "Quantity must be numeric value.";
	public static final String INVALID_PRODUCT_QUANTITY = "Entered Product Quantity is higher than available Stock.";

	// Member Validation error message
	public static final String BLANK_MEMBER_NUMBER = "Please enter Member Number.";
	public static final String INVALID_MEMBER_NUMBER = "Not a valid member.";

	// payment validation
	public static final String REQ_PAYMENT_FIELDS = "Please enter Cash or points to process the Payment.";
	public static final String POINTS_REDEEMED_CONSTRAINT = "Points can be redeemed in multiple of 100.";
	public static final String MAX_POINTS_REDEEMED_MSG = "Maximum points can be redeemed is : ";

	public static final String NOT_ENOUGH_CASH = "Not enough amount to process the payment. ";

	// Manage category message
	public static final String CATEGORY_ADDED_SUCCESSFULLY = "Category has been added successfully. Would like to add another one ?";
	public static final String CATEGORY_EXISTS = "already exists. Would you like to add another one ?";
	public static final String ENTER_CATEGORYID = "Please enter category ID";
	public static final String ENTER_CATEGORYNAME = "Please enter category name";
	public static final String BLANK_CATEGORYID = "Category ID is blank !";
	public static final String BLANK_CATEGORYNAME = "Category name is blank !";
	public static final String CATEGORY_3_LETTERS = "Category ID must be 3 letters";
	public static final String ERROR = "Error occured during";
	public static final String SELECT_CATEGORY = "Please select a category";
	public static final String CATEGORY_REMOVED_SUCCESSFULLY = "Category has been removed successfully !";
	public static final String CATEGORYID_NOT_EXIST = "does not exist";
	public static final String CONFIRM_TO_REMOVE_CATEROGY = "Confirm to remove category ?";

	// Manage Member message
	public static final String MEMBER_ADDED_SUCCESSFULLY = "Member has been added succesfully";
	public static final String BLANK_MEMBER_NUMBERANDNAME = "Please enter Member Name and Member Number";
	public static final String INVALID_NEWMEMBER_NUMBER = "Not a valid member, Please enter a number with 9 Charactor";

	// Manage stock message
	public static final String SELECT_PRODUCT = "Please select a product";
	public static final String STOCK_UPDATED_SUCCESSFULLY = "Stock has been updated successfully !";
	public static final String ENTER_REPLENISH_QUANTITY = "Please enter replenish quantity";
	public static final String BLANK_REPLENISH_QUANTITY = "Replenish quantity is empty !";
	public static final String BLANK_PRODUCT_BAR_CODE = "Bar code is blank !";
	public static final String INVALID_PRODUCT_BAR_CODE = "is not a valid bar code !";
	public static final String INVALID_REPLENISH_QUANTITY = "Please enter a valid replenish quantity";
	public static final String BAR_CODE_NOT_EXIST = "does not exist";

	// Add Product Message
	public static final String EMPTY_QUANTITY = "Please Enter the Quantity greater than 0";
	public static final String EMPTY_PRICE = "Please Enter the Price greater than 0";
	public static final String EMPTY_PRODUCT_NAME = "Please Enter the Product Name. Product Name cannot be Empty";
	public static final String NUMBER_REGEX = "[0-9]";
	public static final String STRING_REGEX = "[a-zA-Z]";

	// StoreKeeper validation message
	public static final String STOREKEEPER_NOT_FOUND = "StoreKeeper not found.";
	public static final String STOREKEEPER_INCORRECT_PASSWORD = "Incorrect password.";

	// Manage discount message
	public static final String SELECT_DISCOUNT = "Please select a discount";
}
