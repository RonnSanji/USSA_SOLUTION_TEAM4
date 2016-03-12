package sg.edu.nus.iss.ssa.model;

/**
 * @author Amarjeet B Singh
 * @param <T>
 *
 */
public class LineItem {
	

	private String productId;
	
	private String productName;	
	
	private long buyQuantity ;
	
	private double price;
	
	private long barCode;
	
	private double totalproductPrice;
	
	
	public LineItem(){
		
	}
	
		
	public LineItem(String productId, String productName, long buyQuantity,
			double price, long barCode) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.buyQuantity = buyQuantity;
		this.price = price;
		this.barCode = barCode;
		this.totalproductPrice = price*buyQuantity;
	}

	/**
	 * returns one dimensioanl array to display in table
	 * @return
	 */
    public String[] getItemsArray() {
    	String[] items = new String[4];
    	items[0] = productName;
    	items[1] = String.valueOf(price);
    	items[2] = String.valueOf(buyQuantity);
    	items[3] = String.valueOf(totalproductPrice);
    	return items;
    	
    }
   


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("product ID: " ).append(productId).append(" prduct Name : ").append(productName).
				append(" product Quantity : ").append(buyQuantity).append(" price : ").append(price).append(" barCode : ").append(barCode).append(" buy Quantity : ").
				append(buyQuantity).append(" toal Price : " ).append(totalproductPrice);
		return sb.toString();
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




	public long getBuyQuantity() {
		return buyQuantity;
	}




	public void setBuyQuantity(long buyQuantity) {
		this.buyQuantity = buyQuantity;
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




	public double getTotalproductPrice() {
		return totalproductPrice;
	}




	public void setTotalproductPrice(double totalproductPrice) {
		this.totalproductPrice = totalproductPrice;
	}	
	
}
