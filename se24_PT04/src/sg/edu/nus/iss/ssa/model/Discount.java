package sg.edu.nus.iss.ssa.model;

/**
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public class Discount extends Entity {

    protected String fileName = "Discounts.dat";
    protected String className = "sg.edu.nus.iss.ssa.model.Discount";
    protected String[] properties = {"discountCode","discountDesc", "starDate","discountPeriod", "discountPerc",
            "applicableTo"};
    protected String mapKey = "discountCode";
}
