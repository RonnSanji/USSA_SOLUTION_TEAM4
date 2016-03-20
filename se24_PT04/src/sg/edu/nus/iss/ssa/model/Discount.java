package sg.edu.nus.iss.ssa.model;

/**
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public abstract class Discount extends Entity {

    protected String fileName = "Discounts.dat";
    protected String className = "sg.edu.nus.iss.ssa.model.PeriodDiscount";
    protected String[] properties = {"discountCode","discountDesc", "starDate","discountPeriod", "discountPerc",
            "applicableTo"};
    protected String mapKey = "discountCode";

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
}
