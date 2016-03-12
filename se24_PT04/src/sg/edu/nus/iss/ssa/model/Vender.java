package sg.edu.nus.iss.ssa.model;

public class Vender extends Entity {
	
	protected String fileName = "vendersCLO.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.Vender";
	protected String[] properties = {"venderId","venderName"};
	protected String mapKey = "venderId";
	
	private String venderId;

	private String venderName;
	
	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}
	
	public String getVenderName() {
		return venderName;
	}
	
	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
}
