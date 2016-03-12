package sg.edu.nus.iss.ssa.model;

public class StoreKeeper extends Entity {

	protected String fileName = "Storekeepers.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.StoreKeeper";
	protected String[] properties = {"skName","skPassword"};
	protected String mapKey = "skName";

	private String skName;
	private String skPassword;
}
