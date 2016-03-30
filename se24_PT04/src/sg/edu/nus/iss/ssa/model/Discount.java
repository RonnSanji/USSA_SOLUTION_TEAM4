package sg.edu.nus.iss.ssa.model;

/**
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public class Discount extends Entity {

    protected String fileName = "Discounts.dat";
    protected String className = "sg.edu.nus.iss.ssa.model.PeriodDiscount";
    protected String[] properties = {"discountCode","discountDesc", "starDate","discountPeriod", "discountPerc",
            "applicableTo"};
    protected String mapKey = "discountCode";

    private String discountCode;
    private String discountDesc;
    private String starDate;
    private String discountPeriod;
    private float discountPerc;
    private String applicableTo;
    
    
    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String[] getProperties() {
        return properties;
    }

    @Override
    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    @Override
    public String getMapKey() {
        return mapKey;
    }

    @Override
    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

	public String getDiscountCode()
	{
		return discountCode;
	}

	public void setDiscountCode(String discountCode)
	{
		this.discountCode = discountCode;
	}

	public String getDiscountDesc()
	{
		return discountDesc;
	}

	public void setDiscountDesc(String discountDesc)
	{
		this.discountDesc = discountDesc;
	}

	public String getStarDate()
	{
		return starDate;
	}

	public void setStarDate(String starDate)
	{
		this.starDate = starDate;
	}

	public String getDiscountPeriod()
	{
		return discountPeriod;
	}

	public void setDiscountPeriod(String discountPeriod)
	{
		this.discountPeriod = discountPeriod;
	}

	public float getDiscountPerc()
	{
		return discountPerc;
	}

	public void setDiscountPerc(float discountPerc)
	{
		this.discountPerc = discountPerc;
	}

	public String getApplicableTo()
	{
		return applicableTo;
	}

	public void setApplicableTo(String applicableTo)
	{
		this.applicableTo = applicableTo;
	}

	
}
