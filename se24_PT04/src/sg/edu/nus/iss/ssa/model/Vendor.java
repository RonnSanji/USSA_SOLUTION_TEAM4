package sg.edu.nus.iss.ssa.model;

public class Vendor extends Entity {

	protected String fileName = "vendors$categoryId.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.Vendor";
	protected String[] properties = { "vendorId", "vendorName" };
	protected String mapKey = "vendorId";

	private String vendorId;

	private String vendorName;

	public Vendor()
	{
		
	}
	
	public Vendor(String categoryID) {
		this.fileName = this.fileName.replace("$categoryId", categoryID);
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String venderId) {
		this.vendorId = venderId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String venderName) {
		this.vendorName = venderName;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String[] getProperties() {
		return properties;
	}
	public void setProperties(String[] properties) {
		this.properties = properties;
	}
	public String getMapKey() {
		return mapKey;
	}
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}
}
