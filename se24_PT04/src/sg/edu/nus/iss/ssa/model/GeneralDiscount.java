package sg.edu.nus.iss.ssa.model;

public abstract class GeneralDiscount extends Entity {

	protected String fileName = "Discounts.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.GeneralDiscount";
	protected String[] properties = {"discountCode","discountDesc", "productDesc","quantity", "price",
			"barCode","thresholdQuantity","orderQuantity"};
	protected String mapKey = "barCode";



}
