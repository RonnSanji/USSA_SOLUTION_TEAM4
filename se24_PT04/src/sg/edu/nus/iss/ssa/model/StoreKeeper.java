package sg.edu.nus.iss.ssa.model;

public class StoreKeeper extends Entity {
	protected String fileName = "StoreKeepers.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.StoreKeeper";
	protected String[] properties = {"userName","password"};
	protected String mapKey = "userName";
	
	private String userName;
	private String password;
	
	public String getFileName() {
		return fileName;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
