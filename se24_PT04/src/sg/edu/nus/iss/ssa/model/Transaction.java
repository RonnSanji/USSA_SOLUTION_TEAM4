package sg.edu.nus.iss.ssa.model;

/**
 * @author Amarjeet B Singh
 * @param <T>
 *
 */
public class Transaction extends Entity{
	
	protected String fileName = "transactions.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.Transaction";
	protected String[] properties = {"transactionId","productId","memberId","quantity","date"};
	protected String mapKey = "transactionId";


	private String transactionId;
	private String productId;
	private String memberId;
	private String quantity;
	private String date;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	
}
