package sg.edu.nus.iss.ssa.constants;

public class StoreConstants {

	public static final String FIELD_DELIMITER = ",";
	public static final int CASH_EQ_POINTS = 100;
	public static final int CATEGORY_ID_MAX_LENGTH = 3;

	public static final String INVALID_PRODUCT = "Please Select a valid product.";
	public static final String BLANK_INPUT_FOR_PRODUCT_PURCHASE = "Please enter BarCode and Quantity to purchase the product.";
	public static final String PRODUCT_QUANTITY_NON_NUMERIC = "Quantity must be numeric value.";
	public static final String INVALID_PRODUCT_QUANTITY = "Entered Product Quantity is higher than available Stock.";

	//Member Validation error message
	public static final String BLANK_MEMBER_NUMBER = "Please enter Member Number.";
	public static final String INVALID_MEMBER_NUMBER = "Not a valid member.";

	//payment validation
	public static final String REQ_PAYMENT_FIELDS = "Please enter Cash or points to process the Payment.";
	public static final String POINTS_REDEEMED_CONSTRAINT = "Points can be redeemed in multiple of 100.";
	public static final String MAX_POINTS_REDEEMED_MSG = "Maximum points can be redeemed is : ";

	public static final String NOT_ENOUGH_CASH = "Not enough amount to process the payment. ";

	// Manage category message
	public static final String CATEGORY_ADDED_SUCCESSFULLY = "Category has been added successfully. Would like to add another one ?";
	public static final String CATEGORY_EXISTS = "already exists. Would you like to add another one ?";
	public static final String BLANK_CATEGORYID = "Please enter category ID";
	public static final String BLANK_CATEGORYNAME = "Please enter category name";
	public static final String CATEGORY_3_LETTERS = "Category ID must be 3 letters";
	public static final String ERROR = "Error occured during";
	
	// Manage stock message
	public static final String SELECT_PRODUCT = "Please select a product";
	public static final String STOCK_UPDATED_SUCCESSFULLY = "Stock has been updated successfully !";
	public static final String BLANK_REPLENISH_QUANTITY = "Please enter replenish quantity";






}
