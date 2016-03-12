package sg.edu.nus.iss.ssa.model;

/**
 * @author Amarjeet B Singh
 * @param <T>
 *
 */
public class Product extends Entity{
	
	protected String fileName = "products.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.Product";
	protected String[] properties = {"productId","productName", "productDesc","quantity", "price",
		   "barCode","thresholdQuantity","orderQuantity"};
	protected String mapKey = "barCode";


	private String productId;
	
	private String productName;
	
	private String productDesc;
	
	private long quantity ;
	
	private double price;
	
	private long barCode;
	
	private long thresholdQuantity;
	
	private long orderQuantity;
	
	public Product(){
		
	}
	
	public Product(String productId, String productName, String productDesc,
			long quantity, double price, long barCode, long thresholdQuantity,
			long orderQuantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDesc = productDesc;
		this.quantity = quantity;
		this.price = price;
		this.barCode = barCode;
		this.thresholdQuantity = thresholdQuantity;
		this.orderQuantity = orderQuantity;
	}


	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("product ID: " ).append(productId).append(" prduct Name : ").append(productName).append(" product description : " ).append(productDesc).
				append(" product Quantity : ").append(quantity).append(" price : ").append(price).append(" barCode : ").append(barCode).append(" threshold Quantity : ").
				append(thresholdQuantity).append(" order Quantity : " ).append(orderQuantity);
		return sb.toString();
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getBarCode() {
		return barCode;
	}

	public void setBarCode(long barCode) {
		this.barCode = barCode;
	}

	public long getThresholdQuantity() {
		return thresholdQuantity;
	}

	public void setThresholdQuantity(long thresholdQuantity) {
		this.thresholdQuantity = thresholdQuantity;
	}

	public long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getMapKey() {
		return mapKey;
	}
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}
	
	
}
